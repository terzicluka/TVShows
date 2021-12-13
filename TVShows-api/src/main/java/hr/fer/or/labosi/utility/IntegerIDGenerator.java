package hr.fer.or.labosi.utility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IntegerIDGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Connection connection = session.connection();

		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select count(showid) as Id from shows");

			if (rs.next()) {
				int id = rs.getInt(1) + 101;
				int generatedId = Integer.valueOf(id);
				System.out.println("Generated Id: " + generatedId);
				return generatedId;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
}