package lotr.common.util.representation;

import java.util.Random;

/**
 * This class is meant to add support for multiple trades under a single entry. It allows the representation of trades
 * that are mutually exclusive with other trades. It extends ExtendedTradeEntry to allow it to be stored with other
 * trade entries but you should not directly call getMin() or getItem() on an instance of it. Instead, if a list contains
 * an entry that is an instance of this class call pickRandom() and then use that reference to get the details of the
 * trade entry.
 *
 * @author richardb122
 */
public class ExtendedMultitradeEntry extends ExtendedTradeEntry {

    private ExtendedTradeEntry[] entries;

    public ExtendedMultitradeEntry(ExtendedTradeEntry[] entries) {
        super(null, -1, -1);
        this.entries = entries;
    }

    public ExtendedTradeEntry pickRandom() {
        Random random = new Random();
        int index = random.nextInt(entries.length);
        return entries[index];
    }
}
