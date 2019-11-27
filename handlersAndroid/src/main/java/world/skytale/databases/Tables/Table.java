package world.skytale.databases.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import world.skytale.database.ItemNotFoundException;
import world.skytale.databases.SQLDatabaseHelper;

public abstract class Table<DAO, ID> {

    protected final SQLDatabaseHelper sqlDatabaseHelper;

    protected Table(SQLDatabaseHelper sqlDatabaseHelper) {
        this.sqlDatabaseHelper = sqlDatabaseHelper;
    }

    protected abstract DAO readFromCursor(Cursor cursor);
    protected abstract ContentValues putIntoContentValues(DAO dao);
    protected abstract String getTableName();
    protected abstract String getWhereCondition(ID id);

    public boolean addData(DAO dao)
    {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = putIntoContentValues(dao);
        long result = db.insert(getTableName(), null, contentValues);
        sqlDatabaseHelper.close();
        if(result == -1) return false;
        return true;
    }

    public boolean insertORReplace(DAO dao)
    {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = putIntoContentValues(dao);
        long result =  db.replace(getTableName(),null,contentValues);
        sqlDatabaseHelper.close();
        if(result == -1) return false;
        return true;
    }


    public DAO getData(ID id) throws ItemNotFoundException {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();


        String query = "SELECT  * FROM " + getTableName() +
                "\n WHERE " + getWhereCondition(id)+";";

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.getCount() == 0) {
            throw new ItemNotFoundException(getTableName(), id.toString());
        }
        cursor.moveToFirst();
        DAO dao = readFromCursor(cursor);
        return dao;
    }
    public boolean updateData(DAO dao, ID id)
    {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = putIntoContentValues(dao);
        int result =  db.update(TableContacts.TABLE_NAME, contentValues, getWhereCondition(id), null);

        db.close();
        if(result>0)
        {
            return true;
        }
        return false;

    }


    public ArrayList<DAO> getAll()
    {
        SQLiteDatabase db = sqlDatabaseHelper.getWritableDatabase();
        String query = "SELECT  * FROM " + getTableName();
        Cursor cursor = db.rawQuery(query,null);
        db.close();
        return readAllFromCursor(cursor);
    }

    protected ArrayList<DAO> readAllFromCursor(Cursor cursor)
    {
        ArrayList<DAO> list = new ArrayList<DAO>();
        if(cursor==null||cursor.getCount()==0)
        {
            return list;
        }

        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            list.add(readFromCursor(cursor));
        }
        return list;
    }




}
