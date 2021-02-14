package run;

import jdbc.model.Portion;

import java.io.*;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //https://coderoad.ru/134492/%D0%9A%D0%B0%D0%BA-%D1%81%D0%B5%D1%80%D0%B8%D0%B0%D0%BB%D0%B8%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D1%8C-%D0%BE%D0%B1%D1%8A%D0%B5%D0%BA%D1%82-%D0%B2-%D1%81%D1%82%D1%80%D0%BE%D0%BA%D1%83

        System.out.println("Старт программы");
        Portion model = Portion.create("1", "test");
        String string = toString(model);
        System.out.println(" Encoded serialized version " );
        System.out.println( string );
        Portion some = ( Portion ) fromString( string );
        System.out.println( "\n\nReconstituted object");
        System.out.println( some );
    }


    /** Read the object from Base64 string. */
    private static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

}
