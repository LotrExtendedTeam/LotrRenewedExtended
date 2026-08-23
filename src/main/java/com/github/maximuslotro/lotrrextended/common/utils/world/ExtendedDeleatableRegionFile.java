package com.github.maximuslotro.lotrrextended.common.utils.world;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.RegionBitmap;
import net.minecraft.world.chunk.storage.RegionFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Modified version of {@link RegionFile} to support deleting chunks
 */
public class ExtendedDeleatableRegionFile implements AutoCloseable {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ByteBuffer PADDING_BUFFER = ByteBuffer.allocateDirect(1);
	private final FileChannel file;
	private final Path externalFileDir;
	private final ByteBuffer header = ByteBuffer.allocateDirect(8192);
	private final IntBuffer offsets;
	private final IntBuffer timestamps;
	protected final RegionBitmap usedSectors = new RegionBitmap();

	public ExtendedDeleatableRegionFile(File pRegionFile, File pContainingFolder, boolean pSync) throws IOException {
		this(pRegionFile.toPath(), pContainingFolder.toPath(), pSync);
	}

	public ExtendedDeleatableRegionFile(Path pRegionFile, Path pContainingFolder, boolean pSync) throws IOException {
		if (!Files.isDirectory(pContainingFolder)) {
			throw new IllegalArgumentException("Expected directory, got " + pContainingFolder.toAbsolutePath());
		} else {
			this.externalFileDir = pContainingFolder;
			this.offsets = this.header.asIntBuffer();
			((Buffer)this.offsets).limit(1024);
			((Buffer)this.header).position(4096);
			this.timestamps = this.header.asIntBuffer();
			if (pSync) {
				this.file = FileChannel.open(pRegionFile, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
			} else {
				this.file = FileChannel.open(pRegionFile, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
			}

			this.usedSectors.force(0, 2);
			((Buffer)this.header).position(0);
			int i = this.file.read(this.header, 0L);
			if (i != -1) {
				if (i != 8192) {
					LOGGER.warn("Region file {} has truncated header: {}", pRegionFile, i);
				}

				long j = Files.size(pRegionFile);

				for(int k = 0; k < 1024; ++k) {
					int l = this.offsets.get(k);
					if (l != 0) {
						int i1 = getSectorNumber(l);
						int j1 = getNumSectors(l);
						if (i1 < 2) {
							LOGGER.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", pRegionFile, k, i1);
							this.offsets.put(k, 0);
						} else if (j1 == 0) {
							LOGGER.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", pRegionFile, k);
							this.offsets.put(k, 0);
						} else if ((long)i1 * 4096L > j) {
							LOGGER.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", pRegionFile, k, i1);
							this.offsets.put(k, 0);
						} else {
							this.usedSectors.force(i1, j1);
						}
					}
				}
			}

		}
	}

	private Path getExternalChunkPath(ChunkPos pChunkPos) {
		String s = "c." + pChunkPos.x + "." + pChunkPos.z + ".mcc";
		return this.externalFileDir.resolve(s);
	}

	private static int getNumSectors(int pPackedSectorOffset) {
		return pPackedSectorOffset & 255;
	}

	private static int getSectorNumber(int pPackedSectorOffset) {
		return pPackedSectorOffset >> 8 & 16777215;
	}

	private static int sizeToSectors(int pSize) {
		return (pSize + 4096 - 1) / 4096;
	}

	public void flush() throws IOException {
		this.file.force(true);
	}

	private void writeHeader() throws IOException {
		((Buffer)this.header).position(0);
		this.file.write(this.header, 0L);
	}

	private int getOffset(ChunkPos pChunkPos) {
		return this.offsets.get(getOffsetIndex(pChunkPos));
	}

	public boolean hasChunk(ChunkPos pChunkPos) {
		return this.getOffset(pChunkPos) != 0;
	}

	private static int getOffsetIndex(ChunkPos pChunkPos) {
		return pChunkPos.getRegionLocalX() + pChunkPos.getRegionLocalZ() * 32;
	}

	@Override
	public void close() throws IOException {
		try {
			this.padToFullSector();
		} finally {
			try {
				this.file.force(true);
			} finally {
				this.file.close();
			}
		}

	}

	private void padToFullSector() throws IOException {
		int i = (int)this.file.size();
		int j = sizeToSectors(i) * 4096;
		if (i != j) {
			ByteBuffer bytebuffer = PADDING_BUFFER.duplicate();
			((Buffer)bytebuffer).position(0);
			this.file.write(bytebuffer, (long)(j - 1));
		}

	}

	/**
	 * Resolves the .mca region file path for a given chunk position in a world directory.
	 * @param regionFolder Folder for world
	 * @param chunkPos Chunk coordinates 
	 * @return Path to region file containing the chunkPos
	 */
	public static File getRegionFilePath(File regionFolder, ChunkPos chunkPos) {
		int regionX = chunkPos.x >> 5;
		int regionZ = chunkPos.z >> 5;
		return new File(regionFolder, "r." + regionX + "." + regionZ + ".mca");
	}
	/**
	 * Deletes a chunk from the region file.
	 * <p>
	 * Clears the offset and timestamp entries in the header, deletes any external
	 * {@code .mcc} file, and frees up the used sectors so future chunk writes can reuse them.
	 *
	 * @param pChunkPos Position of the chunk to delete.
	 * @throws IOException If writing the updated header or deleting an external file fails.
	 */
	public synchronized void deleteChunk(ChunkPos pChunkPos) throws IOException {
		int i = getOffsetIndex(pChunkPos);
		int j = this.offsets.get(i);
		if (j == 0) {
			return; 
		}
		int sectorNumber = getSectorNumber(j);
		int numSectors = getNumSectors(j);
		// Wipe the offset and timestamp from the header tables
		this.offsets.put(i, 0);
		this.timestamps.put(i, 0);
		this.writeHeader();
		// Remove external file if the chunk was over 1MB
		Path externalPath = this.getExternalChunkPath(pChunkPos);
		Files.deleteIfExists(externalPath);
		// Mark the sectors as free so new chunks can reuse the space
		if (sectorNumber != 0) {
			this.usedSectors.free(sectorNumber, numSectors);
		}
	}

	public synchronized long getChunkSizeBytes(ChunkPos pChunkPos) throws IOException {
		int i = getOffset(pChunkPos);
		if (i == 0) return 0L;

		int sectorNumber = getSectorNumber(i);
		int numSectors = getNumSectors(i);

		// Read the 4-byte payload size header inside the sector
		ByteBuffer bytebuffer = ByteBuffer.allocate(5);
		this.file.read(bytebuffer, (long) sectorNumber * 4096L);
		((Buffer) bytebuffer).flip();

		long internalDataSize = 0L;
		if (bytebuffer.remaining() >= 5) {
			int length = bytebuffer.getInt();
			byte version = bytebuffer.get();

			// Check if chunk is stored in an external .mcc file (>1MB)
			if ((version & 128) != 0) {
				Path externalPath = this.getExternalChunkPath(pChunkPos);
				if (Files.isRegularFile(externalPath)) {
					return 4096L + Files.size(externalPath); // Sector stub + external file size
				}
			}
			internalDataSize = length + 4; // Data size + 4-byte size header
		}

		return internalDataSize > 0 ? internalDataSize : (long) numSectors * 4096L;
	}
}