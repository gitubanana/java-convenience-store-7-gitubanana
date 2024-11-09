package store.model.promotion;

import static store.constant.StoreInfo.PROMOTIONS_DATE_FORMAT;
import static store.constant.StoreInfo.PROMOTIONS_DELIMITER;

import java.util.ArrayList;
import java.util.List;
import store.util.Converter;
import store.util.FileLines;
import store.util.Spliter;

public class PromotionsLoader {
    private final List<Promotion> promotions;

    public PromotionsLoader(String file) {
        FileLines fileLines = new FileLines(file);
        fileLines.nextLine(); // ignore first line

        promotions = new ArrayList<>();
        while (true) {
            String line = fileLines.nextLine();
            if (line == null) {
                break;
            }

            addPromotion(line);
        }
    }

    private void addPromotion(String line) {
        Spliter spliter = new Spliter(line, PROMOTIONS_DELIMITER);

        promotions.add(
                new Promotion(
                        spliter.nextToken(),
                        new FreePolicy(
                                Converter.toInteger(spliter.nextToken()),
                                Converter.toInteger(spliter.nextToken())
                        ),
                        new Period(
                                Converter.toLocalDateTime(spliter.nextToken(), PROMOTIONS_DATE_FORMAT),
                                Converter.toLocalDateTime(spliter.nextToken(), PROMOTIONS_DATE_FORMAT)
                        )
                )
        );
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
