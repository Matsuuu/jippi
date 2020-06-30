package matsu.jippi.pojo.common;

public class StockState {
    private StockType stock;

    public StockType getStock() {
        return stock;
    }

    public void setStock(StockType stock) {
        this.stock = stock;
    }

    public StockState(StockType stock) {
        this.stock = stock;
    }
}
