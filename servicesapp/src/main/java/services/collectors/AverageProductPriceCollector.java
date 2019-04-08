package services.collectors;

import model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class AverageProductPriceCollector implements Collector<Product, AverageProductPriceCollector.ProductPriceSummary, BigDecimal> {

  static class ProductPriceSummary {

    private BigDecimal sum = BigDecimal.ZERO;
    private int n;

  }

  @Override
  public Supplier<ProductPriceSummary> supplier() {
    return ProductPriceSummary::new;
  }

  @Override
  public BiConsumer<ProductPriceSummary, Product> accumulator() {
    return (a, p) -> {
      a.sum = a.sum.add(p.getPrize());
      a.n += 1;
    };
  }

  @Override
  public BinaryOperator<ProductPriceSummary> combiner() {
    return (a, b) -> {
      ProductPriceSummary s = new ProductPriceSummary();
      s.sum = a.sum.add(b.sum);
      s.n = a.n + b.n;
      return s;
    };
  }

  @Override
  public Function<ProductPriceSummary, BigDecimal> finisher() {
    return s -> s.n == 0 ? BigDecimal.ZERO : s.sum.divide(BigDecimal.valueOf(s.n), RoundingMode.CEILING);
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.emptySet();
  }

}
