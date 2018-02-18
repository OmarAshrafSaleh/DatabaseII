import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

public class DBApp {
	static int KeyLocation = 0;
	static Hashtable<String, Table> Tables = new Hashtable<String, Table>();


	public static void createTable(String strTableName,
			Hashtable<String, String> htblColNameType) throws DBAppException {
		Table x = new Table(strTableName);
		Tables.put(strTableName, x);
		String[] v = new String[htblColNameType.size()];
		x.Pages.add(new Page());
		Collection<String> Value = htblColNameType.values();
		Value.toArray(v);
		for (int i = 0; i < v.length; i++) {
			x.Types.add(v[i]);

		}
		Set<String> e = htblColNameType.keySet();
		String[] t = new String[e.size()];
		e.toArray(t);
		for (int i = 0; i < t.length; i++) {
			x.Types.add(htblColNameType.get(t[i]));
			if (t[i].equals("id"))
				KeyLocation = i;

			// System.out.println(x.Types.get(i));

		}
		//System.out.println("" + KeyLocation);
	}

	public static void insertIntoTable(String strTableName,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {

		Table Temp = Tables.get(strTableName);
		ArrayList<Object> TempRow = new ArrayList<Object>();
		boolean flag = false;

		Object[] v = new Object[htblColNameValue.size()];
		Collection<Object> Value = htblColNameValue.values();
		Value.toArray(v);
		for (int i = 0; i < v.length; i++) {
			Temp.Values.add(v[i]);

		}
		Set<String> e = htblColNameValue.keySet();
		String[] t = new String[e.size()];
		e.toArray(t);
		for (int i = 0; i < t.length; i++) {
			if (!(htblColNameValue.get(t[i]).getClass().getName()
					.equals(Temp.Types.get(i))))
			{
				System.out.println((htblColNameValue.get(t[i]).getClass().getName()));
				System.out.println(Temp.Types.get(i));
				System.out.println("Fuck"+i);
				throw new DBAppException(
						"Input does not match column's type !!");
			}
			else{
				Temp.Values.add(htblColNameValue.get(t[i]));
			//System.out.println("Dafque"+i);
			}

		}

		for (int j = 0; j < Temp.Pages.size(); j++) {
			if(Temp.Pages.get(j).Rows.size()==0){
				System.out.println("Page number: "+j);
				System.out.println(Temp.Pages.get(j).Rows.size());
				
				Temp.Pages.get(j).Rows.add(Temp.Values);
				
			flag = true;
			
			}
		
				
			else if (Temp.Pages.get(j).Rows.size() < 200 && flag) {
				for (int k = 0; k < Temp.Pages.get(j).Rows.size(); k++) {
					if ((int) (Temp.Pages.get(j).Rows.get(k).get(KeyLocation)) > (int) (Temp.Values
							.get(KeyLocation))) {
						System.out.println("current row :"+k);
						Temp.Pages.get(j).Rows.add(k, Temp.Values);
						System.out.println("Page number: "+j+ "  "+"Row number: "+k);
						flag = true;
						break;
					}
				}
			}

		}
		if (!flag) {
			int LastPage = Temp.Pages.size() - 1;
			int LastRow = Temp.Pages.get(LastPage).Rows.size() - 1;
			Temp.Pages.add(new Page());
			TempRow = Temp.Pages.get(LastPage).Rows.get(LastRow);
			if ((int) (TempRow.get(KeyLocation)) > (int) (Temp.Values
					.get(KeyLocation))) {
				Temp.Pages.get(Temp.Pages.size() - 1).Rows.add(TempRow);
				Temp.Pages.get(Temp.Pages.size() - 2).Rows.remove(LastRow);
				Temp.Pages.get(Temp.Pages.size() - 2).Rows.add(Temp.Values);
				System.out.println("Swap swap");
			} else {
				Temp.Pages.get(Temp.Pages.size() - 1).Rows.add(Temp.Values);
				System.out.println("No Swap swap");
				System.out.println(Temp.Pages.get(Temp.Pages.size() - 1).Rows.size());

			}

		}

	}

	public static void main(String[] args) throws DBAppException {
		String strTableName = "Student";
		Hashtable htblColNameType = new Hashtable( );
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("gpa", "java.lang.Double");
		createTable( strTableName,htblColNameType );
		//createBRINIndex( strTableName, "gpa" );
		Hashtable htblColNameValue = new Hashtable( );
		htblColNameValue.put("id", new Integer( 2343432 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 453455 ));
		htblColNameValue.put("name", new String("Ahmed Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 5674567 ));
		htblColNameValue.put("name", new String("Dalia Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.25 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 23498 ));
		htblColNameValue.put("name", new String("John Noor" ) );
		htblColNameValue.put("gpa", new Double( 1.5 ) );
		insertIntoTable( strTableName , htblColNameValue );
		htblColNameValue.clear( );
		htblColNameValue.put("id", new Integer( 78452 ));
		htblColNameValue.put("name", new String("Zaky Noor" ) );
		htblColNameValue.put("gpa", new Double( 0.88 ) );
		insertIntoTable( strTableName , htblColNameValue );
		
System.out.println(Tables.get(strTableName).Values);		
	}
}