package hibernate;
// Generated Dec 17, 2019 10:48:32 AM by Hibernate Tools 4.3.5.Final

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * LoginlogId generated by hbm2java
 */
public class LoginlogId implements java.io.Serializable {

	private Integer userid;
	private Timestamp logintime;

	public LoginlogId() {
	}

	public LoginlogId(Integer userid, Timestamp logintime) {
		this.userid = userid;
		this.logintime = logintime;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Serializable getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LoginlogId))
			return false;
		LoginlogId castOther = (LoginlogId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this.getUserid() != null
				&& castOther.getUserid() != null && this.getUserid().equals(castOther.getUserid())))
				&& ((this.getLogintime() == castOther.getLogintime()) || (this.getLogintime() != null
						&& castOther.getLogintime() != null && this.getLogintime().equals(castOther.getLogintime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result + (getLogintime() == null ? 0 : this.getLogintime().hashCode());
		return result;
	}

}
