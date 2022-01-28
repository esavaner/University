package watki;
/**Klasa jednego watku programu */
class Multi extends Thread {
	private volatile boolean sw = true;
	private int liczba;
	public void setPr(int liczba) {
		this.liczba = liczba;
	}
	/**Funkcja startujaca watek jednego prostokata */
	public void run() {
		while(sw == true) {
			try {
				MyPanel.prostokaty.get(liczba).changeColor();
				this.sleep(MyPanel.gen.nextInt(Integer.parseInt(MyDialog.k.getText())) + 0,5 * Integer.parseInt(MyDialog.k.getText()));	
			}
			catch (InterruptedException i) {
				System.out.println("Interrupted");
			}
		}
	}
	/**Funkcja resetujaca wszystkie watki przy zmienieniu parametrow */
	public void stopThread() {
		sw = false;
	}
}