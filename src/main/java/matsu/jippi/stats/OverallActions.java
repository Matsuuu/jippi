package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import matsu.jippi.pojo.common.ConversionByPlayerByOpening;
import matsu.jippi.pojo.common.ConversionType;
import matsu.jippi.pojo.common.OverallType;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PlayerInput;
import matsu.jippi.pojo.common.RatioType;
import matsu.jippi.pojo.common.StockType;

public class OverallActions {
    public static List<OverallType> generateOverallStats(List<PlayerIndexedType> playerIndices,
            List<PlayerInput> inputs, List<StockType> stocks, List<ConversionType> conversions,
            int playableFrameCount) {

        Map<Integer, List<PlayerInput>> inputsByPlayer = getInputsByPlayer(inputs);
        Map<Integer, List<StockType>> stocksByPlayer = getStocksByPlayer(stocks);
        Map<Integer, List<ConversionType>> conversionsByPlayer = getConversionsByPlayer(conversions);
        ConversionByPlayerByOpening conversionsByPlayerByOpening = getConversionsByPlayerByOpening(conversionsByPlayer);

        int gameMinutes = playableFrameCount / 3600;

        List<OverallType> overall = new ArrayList<>();
        for (PlayerIndexedType playerIndexType : playerIndices) {

            int inputCount = inputsByPlayer.get(playerIndexType.getPlayerIndex()).size();
            List<ConversionType> playerConversions = conversionsByPlayer.get(playerIndexType.getPlayerIndex());
            if (playerConversions == null) {
                playerConversions = new ArrayList<>();
            }
            List<ConversionType> successfulConversions = playerConversions.stream()
                    .filter(con -> con.getMoves().size() > 1).collect(Collectors.toList());

            List<StockType> opponentStocks = stocksByPlayer.get(playerIndexType.getOpponentIndex());
            if (opponentStocks == null) {
                opponentStocks = new ArrayList<>();
            }
            List<StockType> opponentEndedStocks = opponentStocks.stream()
                    .filter(sto -> sto.getDurationType().getEndFrame() != null).collect(Collectors.toList());

            int totalDamage = getTotalDamage(opponentStocks);

            int conversionCount = playerConversions.size();
            int successfulConversionCount = successfulConversions.size();
            int killCount = opponentEndedStocks.size();

            overall.add(new OverallType(playerIndexType, inputCount, conversionCount, totalDamage, killCount,
                    getRatio(successfulConversionCount, conversionCount), getRatio(inputCount, gameMinutes),
                    getRatio(conversionCount, killCount), getRatio(totalDamage, conversionCount),
                    getOpeningRatio(conversionsByPlayerByOpening, playerIndexType.getPlayerIndex(),
                            playerIndexType.getOpponentIndex(), "neutral-win"),
                    getOpeningRatio(conversionsByPlayerByOpening, playerIndexType.getPlayerIndex(),
                            playerIndexType.getOpponentIndex(), "counter-attack"),
                    getBeneficialTradeatio(conversionsByPlayerByOpening, playerIndexType.getPlayerIndex(),
                            playerIndexType.getOpponentIndex())));
        }

        return null;
    }

    private static RatioType getRatio(int count, Integer total) {
        return new RatioType(count, total, total != null && total != 0 ? count / total : null);
    }

    private static RatioType getOpeningRatio(ConversionByPlayerByOpening conversionsByPlayerByOpening, int playerIndex,
            int opponentIndex, String type) {

        Map<String, Map<String, List<ConversionType>>> conversion = conversionsByPlayerByOpening.getConversion();
        String playerIndexAsString = Integer.toString(playerIndex);
        String opponentIndexAsString = Integer.toString(opponentIndex);

        List<ConversionType> openings = conversion.get(playerIndexAsString) != null
                ? conversion.get(playerIndexAsString).get(type)
                : new ArrayList<>();
        List<ConversionType> opponentOpenings = conversion.get(opponentIndexAsString) != null
                ? conversion.get(opponentIndexAsString).get(type)
                : new ArrayList<>();

        return getRatio(openings.size(), openings.size() + opponentOpenings.size());
    }

    private static RatioType getBeneficialTradeatio(ConversionByPlayerByOpening conversionsByPlayerByOpening,
            int playerIndex, int opponentIndex) {

        String playerIndexAsString = Integer.toString(playerIndex);
        String opponentIndexAsString = Integer.toString(opponentIndex);
        Map<String, Map<String, List<ConversionType>>> conversion = conversionsByPlayerByOpening.getConversion();

        List<ConversionType> playerTrades = conversion.get(playerIndexAsString) != null
                ? conversion.get(playerIndexAsString).get("trade")
                : new ArrayList<>();
        List<ConversionType> opponentTrades = conversion.get(opponentIndexAsString) != null
                ? conversion.get(opponentIndexAsString).get("trade")
                : new ArrayList<>();

        List<ConversionType> benefitsPlayer = new ArrayList<>();
        for (int i = 0; i < playerTrades.size(); i++) {
            ConversionType playerConversion = playerTrades.get(i);
            ConversionType opponentConversion = opponentTrades.get(i);

            float playerDamage = playerConversion.getDamageType().getCurrentPercent()
                    - playerConversion.getDamageType().getStartPercent();
            float opponentDamage = opponentConversion.getDamageType().getCurrentPercent()
                    - opponentConversion.getDamageType().getStartPercent();

            if (playerConversion.isDidKill() && !opponentConversion.isDidKill()) {
                benefitsPlayer.add(playerConversion);
            } else if (playerDamage > opponentDamage) {
                benefitsPlayer.add(playerConversion);
            }
        }

        return getRatio(benefitsPlayer.size(), playerTrades.size());
    }

    private static Map<Integer, List<PlayerInput>> getInputsByPlayer(List<PlayerInput> inputs) {
        Map<Integer, List<PlayerInput>> inputsByPlayer = new HashMap<>();

        for (PlayerInput input : inputs) {
            int playerIndex = input.getPlayerIndex();
            if (!inputsByPlayer.containsKey(playerIndex)) {
                inputsByPlayer.put(playerIndex, new ArrayList<>());
            }
            inputsByPlayer.get(playerIndex).add(input);
        }
        return inputsByPlayer;
    }

    private static Map<Integer, List<StockType>> getStocksByPlayer(List<StockType> stocks) {

        Map<Integer, List<StockType>> stocksByPlayer = new HashMap<>();
        for (StockType stock : stocks) {
            int playerIndex = stock.getPlayerIndexedType().getPlayerIndex();
            if (!stocksByPlayer.containsKey(playerIndex)) {
                stocksByPlayer.put(playerIndex, new ArrayList<>());
            }
            stocksByPlayer.get(playerIndex).add(stock);
        }
        return stocksByPlayer;
    }

    private static Map<Integer, List<ConversionType>> getConversionsByPlayer(List<ConversionType> conversions) {
        Map<Integer, List<ConversionType>> conversionsByPlayer = new HashMap<>();
        for (ConversionType conversion : conversions) {
            int playerIndex = conversion.getPlayerIndexedType().getPlayerIndex();
            if (!conversionsByPlayer.containsKey(playerIndex)) {
                conversionsByPlayer.put(playerIndex, new ArrayList<>());
            }
            conversionsByPlayer.get(playerIndex).add(conversion);
        }
        return conversionsByPlayer;
    }

    private static ConversionByPlayerByOpening getConversionsByPlayerByOpening(
            Map<Integer, List<ConversionType>> conversionsByPlayer) {

        Map<String, Map<String, List<ConversionType>>> conversionsByPlayerByOpening = new HashMap<>();
        for (Map.Entry<Integer, List<ConversionType>> playerConversions : conversionsByPlayer.entrySet()) {

            String playerIndex = Integer.toString(playerConversions.getKey());
            if (!conversionsByPlayerByOpening.containsKey(playerIndex)) {
                conversionsByPlayerByOpening.put(playerIndex, new HashMap<>());
            }
            for (ConversionType conversion : playerConversions.getValue()) {
                String openingType = conversion.getOpeningType();
                if (!conversionsByPlayerByOpening.get(playerIndex).containsKey(openingType)) {
                    conversionsByPlayerByOpening.get(playerIndex).put(openingType, new ArrayList<>());
                }
                conversionsByPlayerByOpening.get(playerIndex).get(openingType).add(conversion);
            }
        }
        return new ConversionByPlayerByOpening(conversionsByPlayerByOpening);
    }

    private static int getTotalDamage(List<StockType> stocks) {
        int totalDamage = 0;
        for (StockType stock : stocks) {
            totalDamage += stock.getDamageType().getCurrentPercent();
        }
        return totalDamage;
    }
}
