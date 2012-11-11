/**
 * 
 */
package logging;

/**
 * @author Momo
 *
 */
public class Logger {
	private String ownner = "********";
	
	public Logger () {
	}
	
	public Logger(String ownner) {
		this.ownner = ownner;
	}
	
	public void setOwnner(String ownner) {
		this.ownner = ownner;
	}
	
	public void info (String content) {
		LogFactory.info("\t" + ownner + System.getProperty("line.separator") + content);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogFactory logFactory = new LogFactory();
		Logger logger = new Logger("Siu-Tung Wang");
		
		for (int i = 0; i < 45; i++)
			logger.info("No." + i);
		logger.info("1--It is a test!\nThank you.");
		logger.info("2--It is a test!\nThank you.");
		logger.info("3--It is a test!\nThank you.");
		logger.info("¹þ¹þ¹þ");
		System.out.println("end>>>>>>>>>>>>>");
	}

}
