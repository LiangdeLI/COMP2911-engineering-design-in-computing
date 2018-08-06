import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
	
	private Clip clip;
	private AudioInputStream inputStream;
	private int timer = 0;
	
	//BGM Credit: 
	//http://freemusicarchive.org/music/Sycamore_Drive/The_Waves_The_Sea/Ocean_Breeze
	//Button credit
	//https://www.soundjay.com/typewriter-sounds.html
	private Clip currentBGM;
	private AudioInputStream bgmStream;
	private FloatControl bgmVolume;
	
	public SoundManager () {
		try {
			currentBGM = AudioSystem.getClip();
			bgmStream = AudioSystem.getAudioInputStream (getClass().getResourceAsStream("sound/menu.wav"));
			currentBGM.open(bgmStream);
			bgmVolume = (FloatControl) currentBGM.getControl(FloatControl.Type.MASTER_GAIN);
			bgmVolume.setValue(-1.0f);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	private void playSound(String name){
		try {						
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("sound/" + name + ".wav"));
			clip.open(inputStream);	        
	        clip.start(); 
	      } catch (Exception e) {
	        System.err.println(e.getMessage());
	      }
	}
	public void playPush(){
		playSound("push");
	}
	
	public void playCollision(){
		if(timer == 0){
			playSound("collision");
			timer = 20;
		}	
	}
	
	public void playVictory(){
		playSound("victory");
	}
	
	public void tick(){
		if(timer > 0){
			timer--;	
		}
	}
	
	public Clip getClip () {
		return clip;
	}
	
	public void playMainMenu () {
		currentBGM.loop(Clip.LOOP_CONTINUOUSLY);
		/*try {
			Thread.sleep();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void stopMainMenu () {
		currentBGM.stop();		
	}
	
	public void playButton () {
		playSound ("button1");
	}

}
