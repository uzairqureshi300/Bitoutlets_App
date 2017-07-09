	package com.bitoutlets_app.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

// A helper class to manage database creation and version management. 
public class AndroidOpenDbHelper extends SQLiteOpenHelper {
	// Database attributes
	public static final String DB_NAME = "Carts";
	public static final int DB_VERSION = 1;

	// Table attributes
	public static final String TABLE_NAME_Cart = "Cart_Table";
	public static final String TABLE_NAME_Whish = "Whish_Table";
	public static final String TABLE_NAME_Compare = "Comapre_Table";
	public static final String product_id = "product_id";
	public static final String product_title = "product_title";
	public static final String product_price = "product_price";
	public static final String product_shipping = "product_shipping";
	public static final String product_features = "product_features";
	public static final String product_tags = "product_tags";
	public static final String product_current_stock = "product_current_stock";
	public static final String product_no_of_views = "product_no_of_views";
	public static final String product_discount = "product_discount";
	public static final String product_tax = "product_tax";
	public static final String product_description = "product_description";
	public static final String product_image = "product_image";
	public static final String product_unit = "unit";
	public static final String product_add = "validate";


	public AndroidOpenDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	// Called when the database is created for the first time. 
	//This is where the creation of tables and the initial population of the tables should happen.
	@Override
	public void onCreate(SQLiteDatabase db) {
		// We need to check whether table that we are going to create is already exists.
		//Because this method get executed every time we created an object of this class. 
		//"create table if not exists TABLE_NAME ( BaseColumns._ID integer primary key autoincrement, FIRST_COLUMN_NAME text not null, SECOND_COLUMN_NAME integer not null);"
		String sqlQueryToCreateUndergraduateDetailsTable = "create table if not exists " + TABLE_NAME_Cart + " ( " + BaseColumns._ID + " integer primary key autoincrement, "
				+ product_id + " text, "
				+ product_title + " text, " +
				product_price + " text, " +
				product_shipping + " text, " +
				product_features + " text, " +
				product_tags + " text, "
				+ product_current_stock + " text, "
				+ product_no_of_views + " text, "
				+ product_discount + " text, " +
				product_tax + " text, "
				+ product_description + " text, "
				+ product_image + " text, "
				+ product_add + " text, "
				+ product_unit + " real);";
		// Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
		db.execSQL(sqlQueryToCreateUndergraduateDetailsTable);


	}

	// onUpgrade method is use when we need to upgrade the database in to a new version
	//As an example, the first release of the app contains DB_VERSION = 1
	//Then with the second release of the same app contains DB_VERSION = 2
	//where you may have add some new tables or alter the existing ones
	//Then we need check and do relevant action to keep our pass data and move with the next structure
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		if (newVersion > oldVersion) {
			String sqlQueryToCreateUndergraduateDetailsTable = "create table if not exists " + TABLE_NAME_Cart + " ( " + BaseColumns._ID + " integer primary key autoincrement, "
					+ product_id + " text, "
					+ product_title + " text, " +
					product_price + " text, " +
					product_shipping + " text, " +
					product_features + " text, " +
					product_tags + " text, "
					+ product_current_stock + " text, "
					+ product_no_of_views + " text, "
					+ product_discount + " text, " +
					product_tax + " text, "
					+ product_description + " text, "
					+ product_image + " text, "
					+ product_add + " text, "
					+ product_unit + " real);";
			// Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
			db.execSQL(sqlQueryToCreateUndergraduateDetailsTable);

		}
	}
}
