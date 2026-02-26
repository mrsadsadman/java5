package poly.edu.assignment.dto;

public class RevenueReportDTO {

    private String categoryName;
    private Double totalRevenue;
    private Long totalQuantity;
    private Double maxPrice;
    private Double minPrice;
    private Double avgPrice;

    public RevenueReportDTO(String categoryName,
            Double totalRevenue,
            Long totalQuantity,
            Double maxPrice,
            Double minPrice,
            Double avgPrice) {
        this.categoryName = categoryName;
        this.totalRevenue = totalRevenue;
        this.totalQuantity = totalQuantity;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.avgPrice = avgPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }
}