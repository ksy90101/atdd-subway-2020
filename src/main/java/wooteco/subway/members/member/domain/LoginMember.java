package wooteco.subway.members.member.domain;

import wooteco.security.core.userdetails.UserDetails;

public class LoginMember implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final Integer age;

    public LoginMember(final Long id, final String email, final String password, final Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public boolean checkPassword(final String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public boolean checkCredentials(final Object credentials) {
        final String password = (String)credentials;
        if (this.password == null || password == null) {
            return false;
        }

        return this.password.equals(password);
    }

    public int discountFare(final int fare) {
        if (age == 0 || age > 18) {
            return fare;
        }
        if (age > 13) {
            return (fare - 350) * 80 / 100;
        }
        if (age >= 6) {
            return (fare - 350) * 50 / 100;
        }
        return 0;
    }
}
