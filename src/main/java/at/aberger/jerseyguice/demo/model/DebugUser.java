package at.aberger.jerseyguice.demo.model;

public class DebugUser extends User {

	@Override
	public String toString() {
		return "DebugUser [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

}
