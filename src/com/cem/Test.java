package com.cem;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	static PreparedStatement ps;
	static ResultSet rs;
	static Connection connect = null;

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/etrade2";
		String kullaniciAdi = "postgres";
		String sifre = "root";
		try {
			Driver.class.forName("org.postgresql.Driver");
			connect = DriverManager.getConnection(url, kullaniciAdi, sifre);
			System.out.println("Baðlantý baþarýlý");
			String sql = "select itemname, unitprice from items group by itemname, unitprice having unitprice > 16";
			findAll(sql);
			System.out.println("****************************************");
			String sql2 = "select townid, district from districts where district ilike 'a%' group by townid, district order by townid";
			findAll2(sql2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Baþarýsýz baðlantý");
			e.printStackTrace();
		} finally {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void execute(Connection connection, String sql) {

		try {
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			System.out.println("Ýþlem baþarýlý.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void findAll(String sql) throws SQLException {

		ps = connect.prepareCall(sql);
		rs = ps.executeQuery();// select sorgusu çalýþtýrýlýr ve geriye bir tablo sonuç. olarak döner

		while (rs.next()) {
			System.out.println(" ----------------------------- ");
			System.out.print("Ürün adý: " + rs.getString(1) + " /// Fiyat: ");
			System.out.print(rs.getDouble(2));
			System.out.println();

		}

	}

	public static void findAll2(String sql) throws SQLException {

		ps = connect.prepareCall(sql);
		rs = ps.executeQuery();// select sorgusu çalýþtýrýlýr ve geriye bir tablo sonuç. olarak döner

		while (rs.next()) {
			System.out.println(" ----------------------------- ");
			System.out.print("Id: " + rs.getInt(1) + " /// Mahalle: ");
			System.out.print(rs.getString(2));
			System.out.println();

		}

	}

}
