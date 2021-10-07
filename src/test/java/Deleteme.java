import java.util.ArrayList;

public class Deleteme
{
	public static void main( String[] args )
	{
		final ArrayList< Double > doubles = new ArrayList<>();
		doubles.add( 10. );
		doubles.add( -100. );
		doubles.add( 50. );
		doubles.sort( ( o1, o2 ) -> Math.abs( o1 ) < Math.abs( o2 ) ? 1 : -1 );
		Math.signum( doubles.get( 0 ) );
	}
}
