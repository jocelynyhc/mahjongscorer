import static java.lang.System.out;

import java.util.Scanner;

public class MahjongPlayer {
	
	public String name;
	public int score;
	public int wins;
	public int losses;
	public static int ROUND = 1;
	
	public MahjongPlayer(String inputName){
		name = inputName;
		score = 0;
		wins = 0;
		losses = 0;
	}
	
	public void win(MahjongPlayer other, int amt){
		this.score+=amt;
		other.score-=amt;
		this.wins++;
		other.losses++;
	}
	
	public void zimo(int amt, MahjongPlayer dealer, int streak, MahjongPlayer[] group){
		//streak 0 = first turn being dealer
		this.wins++;
		for(int x = 0; x < group.length; x++){
			if(!(group[x].equals(this))){
				group[x].score-=amt;
				group[x].losses++;
				this.score+=amt;
			}
		}
		if(!(dealer.equals(this))){
			//additional loss
			int additional = (1 + streak*1 + streak*streak);
			dealer.score-= additional;
			this.score+= additional;
		}
	}
	
	public void resetScore(){
		this.score=0;
		this.wins=0;
		this.losses=0;
	}
	
	public static void printScores(MahjongPlayer[] group){
		for(int x = 0; x < group.length; x++){
			System.out.println("[" + x +"] "+group[x].name+" - Score: " + group[x].score + " (" + group[x].wins + " wins, " + group[x].losses + " losses)");
		}
	}
	
	public static void newRound(MahjongPlayer[] group){
		ROUND++;
		for(int x = 0; x < group.length; x++){
			group[x].resetScore();
		}
	}
	
	public static void main (String[] args){
		
		MahjongPlayer[] fam = new MahjongPlayer[4];
		MahjongPlayer Eunice = new MahjongPlayer("Eunice");
		MahjongPlayer Lawrence = new MahjongPlayer("Lawrence");
		MahjongPlayer Jocelyn = new MahjongPlayer("Jocelyn");
		MahjongPlayer Mom = new MahjongPlayer("Mom");
		MahjongPlayer Dad = new MahjongPlayer("Dad");
		MahjongPlayer Anthony = new MahjongPlayer("Anthony");
		fam[0]=Anthony;
		fam[1]=Lawrence;
		fam[2]=Jocelyn;
		fam[3]=Mom;
		
		String cont;
		int game=0;
		
		System.out.println("* MAHJONG START OF ROUND "+ MahjongPlayer.ROUND + "! *");
		System.out.println();
		do{
			System.out.println("ROUND " + MahjongPlayer.ROUND + " GAME " + game +":");
			MahjongPlayer.printScores(fam);
			System.out.println();
			Scanner keyboard = new Scanner(System.in);
	        out.print("Who won? (input num): ");
	        int winner = keyboard.nextInt();
	        out.print("Did " + fam[winner].name + " zimo? (input y/n): ");
	        String ans = keyboard.next();
	        if(ans.equals("y")){
	        	out.print(fam[winner].name + " zimo-ed for how many points? (input num): ");
	        	int amt = keyboard.nextInt();
	        	out.print("Who was the dealer? (input num): ");
	        	int loser = keyboard.nextInt();
	        	MahjongPlayer dealer = fam[loser];
	        	out.print("What's the dealer's streak? (0 = first turn): ");
	        	int streak = keyboard.nextInt();
	        	fam[winner].zimo(amt, dealer, streak, fam);
	        }
	        else{
		        out.print(fam[winner].name+ " beat who? (input num): ");
		        int loser = keyboard.nextInt();
		        out.print(fam[winner].name + " beat " + fam[loser].name + " for how many points? (input num): ");
		        int amt = keyboard.nextInt();
		        fam[winner].win(fam[loser], amt);
	        }
	        
	        System.out.println();
	        MahjongPlayer.printScores(fam);
	        game++;
	        
	        System.out.println();
	        out.print("Continue? (y/n/round): ");
	        cont = keyboard.next();
	        if(cont.equals("round")){
	        	System.out.println("* ROUND 1 FINAL SCORES! *");
	        	int max=0;
	    		for(int x = 0; x < fam.length; x++){
	    			if(fam[x].score>fam[max].score){
	    				max = x;
	    			}
	    		}
	    		System.out.println();
	    		System.out.println("THE WINNER IS: " + fam[max].name + "!");
	    		System.out.println();
	        	MahjongPlayer.newRound(fam);
	        	cont = "y";
	        }
	        else if(cont.equals("n")){
	        	System.out.println();
	        	System.out.println("* FINAL SCORES! *");
	    		MahjongPlayer.printScores(fam);
	    		
	    		int max=0;
	    		for(int x = 0; x < fam.length; x++){
	    			if(fam[x].score>fam[max].score){
	    				max = x;
	    			}
	    		}
	    		System.out.println();
	    		System.out.println("THE WINNER IS: " + fam[max].name + "!");
			}
		}
		while (cont.equals("y"));        
	}
}
