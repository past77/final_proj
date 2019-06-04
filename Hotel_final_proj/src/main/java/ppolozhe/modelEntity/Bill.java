package ppolozhe.modelEntity;

public class Bill {

    private int id;
    private int price;


    public static class Builder {
        private int id;
        private int price;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Bill build() {
            Bill bill = new Bill();
            bill.setId(id);
            bill.setPrice(price);
            return bill;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (id != bill.id) return false;
        return price == bill.price;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
