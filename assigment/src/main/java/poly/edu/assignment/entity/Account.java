package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullname;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String address;
    private String photo;
    private Boolean activated = false;
    private String token;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @OneToMany(mappedBy = "account")
    private List<Order> orders;
}