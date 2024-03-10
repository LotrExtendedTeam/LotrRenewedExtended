package lotr.common.util.representation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ExtendedTradeEntries {

    public enum TradeType {
        BOUGHT, SOLD
    }

    private final TradeType tradeType;

    private final ExtendedTradeEntry[] entries;

    public ExtendedTradeEntries(TradeType tradeType, ExtendedTradeEntry[] entries) {
        this.tradeType = tradeType;
        this.entries = entries;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public ExtendedTradeEntry[] getEntries() {
        return entries;
    }

    public ArrayList<ExtendedTrade> generateTrades() {
        Random random = new Random();

        ArrayList<ExtendedTrade> trades = new ArrayList<>();
        int tradeCount = random.nextInt(6) + 4;

        List<ExtendedTradeEntry> randomElements;
        if (tradeCount > entries.length) tradeCount = entries.length;
        randomElements = random
                .ints(0, entries.length)
                .distinct()
                .limit(tradeCount)
                .mapToObj(Arrays.asList(entries)::get)
                .collect(Collectors.toList());

        randomElements.forEach(entry -> {
            if (entry instanceof ExtendedMultitradeEntry) {
                entry = ((ExtendedMultitradeEntry) entry).pickRandom();
            }

            int amount;
            int difference = entry.getMax() - entry.getMin();
            if (difference <= 0) {
                amount = entry.getMin();
            } else {
                amount = new Random().nextInt(entry.getMax() - entry.getMin()) + entry.getMin();
            }
            trades.add(new ExtendedTrade(entry.getItem(), amount, entry.getQuantity(), entry.getQuantity()));
        });

        return trades;
    }

}
