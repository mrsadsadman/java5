
package poly.edu.lab7.entity;

public interface Report {
    Category getGroup(); // Đổi từ Serializable thành Category

    Double getSum();

    Long getCount();
}