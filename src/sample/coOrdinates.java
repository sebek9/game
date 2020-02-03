package sample;

public class coOrdinates {

    private String symbol;
    private int row;
    private int column;

    public coOrdinates(String symbol, int row, int column) {
        this.symbol = symbol;
        this.row = row;
        this.column = column;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
