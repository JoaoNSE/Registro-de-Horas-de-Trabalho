package br.qxd.jh.registry.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        })
})
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String username;
	
	@JsonIgnore
	@NotNull
	private String password;
	
	@NotNull
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY,
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH},
			targetEntity=HoursRecord.class)
	@JoinColumn(name="user_id")
	@JsonManagedReference
	private List<HoursRecord> hoursRecords;
	
	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();
	
	public User() {
		super();
		this.username = "";
		this.password = "";
		this.name = "";
	}

	public User(Long id, @NotNull String username, @NotNull String password, @NotNull String name) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	public User(@NotNull String username, @NotNull String password, @NotNull String name) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HoursRecord> getHoursRecords() {
		return hoursRecords;
	}

	public void setHoursRecords(List<HoursRecord> hoursRecords) {
		this.hoursRecords = hoursRecords;
	}
	
	public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
