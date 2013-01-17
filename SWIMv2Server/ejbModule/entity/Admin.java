package entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class Admin extends Profilo implements Serializable {

	private static final long serialVersionUID = 1L;

	public Admin(){
		super();
	}
 
}