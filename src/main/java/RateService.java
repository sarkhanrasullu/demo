import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RateService {

    private static final String FILE_NAME = "FILE.DAT";

    private static final Currency USD = Currency.getInstance("USD");

    // assume method fetches rates from api
    public Map<Currency, BigDecimal> getRates(Currency currency) {
        if (currency == USD) {
            Map<Currency, BigDecimal> rates = new HashMap<>();
            rates.put(Currency.getInstance("GBP"), new BigDecimal("1.654"));
            rates.put(Currency.getInstance("CHF"), new BigDecimal("1.10"));
            rates.put(Currency.getInstance("EUR"), new BigDecimal("1.35"));

            return rates;
        }
        return new HashMap<>();
    }

    public BigDecimal exchange(BigDecimal amount, Currency from, Currency to) {
        final Map<Currency, BigDecimal> usdRates = getRates(USD);
        final BigDecimal fromRateInUsd = usdRates.get(from);
        final BigDecimal toRateInUsd = usdRates.get(to);

        return fromRateInUsd.divide(toRateInUsd, RoundingMode.DOWN);
    }

    public List<CreditRate> getCreditRates(Currency currency) {
        try {
            return Files.readAllLines(Paths.get(this.getClass().getResource(FILE_NAME).toURI()))
                .stream()
                .skip(1)
                .map(CreditRate::parse)
                .map(c -> c.setExchangedAmount(this.exchange(c.getAmount(), c.getCurrency(), currency)))
                .collect(Collectors.toList());
        } catch (Exception e) {
            Logger.getLogger(RateService.class.getName()).log(Level.SEVERE, "credit rates", e);
            return Collections.emptyList();
        }
    }

    public List<Map<String, BigDecimal>> getAvgCreditRates(Currency currency) {
        return getCreditRates(currency)
            .stream()
            .collect(Collectors.groupingBy((c -> Strings.first(c.getCountry(), c.getCity()))))
            .entrySet()
            .stream()
            .map(e -> {
                Map<String, BigDecimal> result = new HashMap<>();
                final BigDecimal avgCreditRate = e.getValue().stream()
                    .map(CreditRate::getExchangedAmount)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .divide(new BigDecimal(e.getValue().size()), RoundingMode.DOWN);
                result.put(e.getKey(), avgCreditRate);
                return result;
            }).collect(Collectors.toList());
    }

}
