import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class CreditRateIntegrationTest {

    private final RateService rateService = new RateService();

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CHF = Currency.getInstance("CHF");

    @Test
    public void testGetCreditRates() {
        final List<CreditRate> creditRates = rateService.getCreditRates(EUR);

        final BigDecimal exchangedAmount = creditRates.stream().filter(c -> c.getCurrency() == CHF).findFirst()
            .orElseThrow(() -> new RuntimeException("currency could not be found"))
            .getExchangedAmount();

        Assert.assertEquals(exchangedAmount.compareTo(new BigDecimal("0.81")), 0);
    }

    @Test
    public void testGetAvgCreditRates() {
        final List<Map<String, BigDecimal>> creditRates = rateService.getAvgCreditRates(EUR);

        final Map<String, BigDecimal> firstElement = creditRates.get(0);

        Assert.assertEquals(firstElement.get("NOR").compareTo(new BigDecimal("1.086")), 0);
    }
}
