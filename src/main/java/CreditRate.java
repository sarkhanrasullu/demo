import java.math.BigDecimal;
import java.util.Currency;

public class CreditRate {

    private String companyCode;
    private String account;
    private String city;
    private String country;
    private String creditRating;
    private Currency currency;
    private BigDecimal amount;
    private BigDecimal exchangedAmount;

    public String getCompanyCode() {
        return companyCode;
    }

    public CreditRate setCompanyCode(final String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public CreditRate setAccount(final String account) {
        this.account = account;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CreditRate setCity(final String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CreditRate setCountry(final String country) {
        this.country = country;
        return this;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public CreditRate setCreditRating(final String creditRating) {
        this.creditRating = creditRating;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CreditRate setCurrency(final Currency currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CreditRate setAmount(final BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getExchangedAmount() {
        return exchangedAmount;
    }

    public CreditRate setExchangedAmount(final BigDecimal exchangedAmount) {
        this.exchangedAmount = exchangedAmount;
        return this;
    }

    public static CreditRate parse(String line) {
        final String[] cells = line.split("\t");
        final String companyCode = cells[0];
        final String account = cells[1];
        final String city = cells[2];
        final String country = cells[3];
        final String creditRating = cells[4];
        final Currency currency = Currency.getInstance(cells[5]);
        final BigDecimal amount = new BigDecimal(cells[6]);

        return new CreditRate()
            .setAccount(account)
            .setAmount(amount)
            .setCity(city)
            .setCompanyCode(companyCode)
            .setCountry(country)
            .setCurrency(currency)
            .setCreditRating(creditRating);
    }

    @Override
    public String toString() {
        return "CreditRate{" +
            "companyCode='" + companyCode + '\'' +
            ", account='" + account + '\'' +
            ", city='" + city + '\'' +
            ", country='" + country + '\'' +
            ", creditRating='" + creditRating + '\'' +
            ", currency=" + currency +
            ", amount=" + amount +
            ", exchangedAmount=" + exchangedAmount +
            '}';
    }

}
