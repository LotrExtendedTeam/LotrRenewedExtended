package com.github.maximuslotro.lotrrextended.common.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * A specialized {@link Inventory} for unit slot lookups.
 * <p>
 * Slot index mapping:
 * <ul>
 *   <li><b>0–8</b>: Food inventory</li>
 *   <li><b>9</b>: Player helmet</li>
 *   <li><b>10</b>: Player chestplate</li>
 *   <li><b>11</b>: Player leggings</li>
 *   <li><b>12</b>: Player boots</li>
 *   <li><b>13</b>: Player melee item</li>
 *   <li><b>14</b>: Player ranged item</li>
 *   <li><b>15</b>: Player mounted melee item</li>
 *   <li><b>16</b>: Player idle item</li>
 *   <li><b>17</b>: Player idle mounted item</li>
 *   <li><b>18</b>: Player offhand item</li>
 *   <li><b>19</b>: Base equipment helmet</li>
 *   <li><b>20</b>: Base equipment chestplate</li>
 *   <li><b>21</b>: Base equipment leggings</li>
 *   <li><b>22</b>: Base equipment boots</li>
 *   <li><b>23</b>: Base equipment melee item</li>
 *   <li><b>24</b>: Base equipment ranged item</li>
 *   <li><b>25</b>: Base equipment mounted melee item</li>
 *   <li><b>26</b>: Base equipment idle item</li>
 *   <li><b>27</b>: Base equipment idle mounted item</li>
 *   <li><b>28</b>: Base equipment offhand item</li>
 * </ul>
 * </p>
 */
public class ExtendedUnitInventory extends Inventory {
    // --- Slot constants ---

//    public static final int SLOT_FOOD_START = 0;
//    public static final int SLOT_FOOD_END = 8;
//
//    public static final int SLOT_PLAYER_HELMET = 9;
//    public static final int SLOT_PLAYER_CHESTPLATE = 10;
//    public static final int SLOT_PLAYER_LEGGINGS = 11;
//    public static final int SLOT_PLAYER_BOOTS = 12;
//    public static final int SLOT_PLAYER_MELEE = 13;
//    public static final int SLOT_PLAYER_RANGED = 14;
//    public static final int SLOT_PLAYER_MOUNTED_MELEE = 15;
//    public static final int SLOT_PLAYER_IDLE = 16;
//    public static final int SLOT_PLAYER_IDLE_MOUNTED = 17;
//    public static final int SLOT_PLAYER_OFFHAND = 18;
//
//    public static final int SLOT_BASE_HELMET = 19;
//    public static final int SLOT_BASE_CHESTPLATE = 20;
//    public static final int SLOT_BASE_LEGGINGS = 21;
//    public static final int SLOT_BASE_BOOTS = 22;
//    public static final int SLOT_BASE_MELEE = 23;
//    public static final int SLOT_BASE_RANGED = 24;
//    public static final int SLOT_BASE_MOUNTED_MELEE = 25;
//    public static final int SLOT_BASE_IDLE = 26;
//    public static final int SLOT_BASE_IDLE_MOUNTED = 27;
//    public static final int SLOT_BASE_OFFHAND = 28;
    
	/**
     * Creates a new {@code UnitInventory}.
     *
     * @param pSize the size of the inventory
     */
	public ExtendedUnitInventory(int pSize) {
		super(pSize);
	}

	/**
     * Gets the {@link ItemStack} stored at the given slot index.
     * <p>
     * Slot index mapping:
     * <ul>
     *   <li><b>0–8</b>: Food inventory</li>
     *   <li><b>9</b>: Player helmet</li>
     *   <li><b>10</b>: Player chestplate</li>
     *   <li><b>11</b>: Player leggings</li>
     *   <li><b>12</b>: Player boots</li>
     *   <li><b>13</b>: Player melee item</li>
     *   <li><b>14</b>: Player ranged item</li>
     *   <li><b>15</b>: Player mounted melee item</li>
     *   <li><b>16</b>: Player idle item</li>
     *   <li><b>17</b>: Player idle mounted item</li>
     *   <li><b>18</b>: Player offhand item</li>
     *   <li><b>19</b>: Base equipment helmet</li>
     *   <li><b>20</b>: Base equipment chestplate</li>
     *   <li><b>21</b>: Base equipment leggings</li>
     *   <li><b>22</b>: Base equipment boots</li>
     *   <li><b>23</b>: Base equipment melee item</li>
     *   <li><b>24</b>: Base equipment ranged item</li>
     *   <li><b>25</b>: Base equipment mounted melee item</li>
     *   <li><b>26</b>: Base equipment idle item</li>
     *   <li><b>27</b>: Base equipment idle mounted item</li>
     *   <li><b>28</b>: Base equipment offhand item</li>
     * </ul>
     * </p>
     *
     * @param pIndex the slot index to retrieve
     * @return the {@link ItemStack} at the given slot index
     */
	@Override
	public ItemStack getItem(int pIndex) {
		return super.getItem(pIndex);
	}

	/**
     * Sets the {@link ItemStack} at the given slot index.
     * <p>
     * Slot index mapping:
     * <ul>
     *   <li><b>0–8</b>: Food inventory</li>
     *   <li><b>9</b>: Player helmet</li>
     *   <li><b>10</b>: Player chestplate</li>
     *   <li><b>11</b>: Player leggings</li>
     *   <li><b>12</b>: Player boots</li>
     *   <li><b>13</b>: Player melee item</li>
     *   <li><b>14</b>: Player ranged item</li>
     *   <li><b>15</b>: Player mounted melee item</li>
     *   <li><b>16</b>: Player idle item</li>
     *   <li><b>17</b>: Player idle mounted item</li>
     *   <li><b>18</b>: Player offhand item</li>
     *   <li><b>19</b>: Base equipment helmet</li>
     *   <li><b>20</b>: Base equipment chestplate</li>
     *   <li><b>21</b>: Base equipment leggings</li>
     *   <li><b>22</b>: Base equipment boots</li>
     *   <li><b>23</b>: Base equipment melee item</li>
     *   <li><b>24</b>: Base equipment ranged item</li>
     *   <li><b>25</b>: Base equipment mounted melee item</li>
     *   <li><b>26</b>: Base equipment idle item</li>
     *   <li><b>27</b>: Base equipment idle mounted item</li>
     *   <li><b>28</b>: Base equipment offhand item</li>
     * </ul>
     * </p>
     *
     * @param pIndex the slot index to modify
     * @param pStack the {@link ItemStack} to place in the slot
     */
	@Override
	public void setItem(int pIndex, ItemStack pStack) {
		super.setItem(pIndex, pStack);
	}
}
