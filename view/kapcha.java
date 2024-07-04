package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class kapcha {
    public int mathematic_kapch(){
        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1;
        int num_1 = random.nextInt(50)+1 ;
        int num_2 = random.nextInt(50)+1 ;
        switch (randomNumber){
            case 1:
                System.out.println(num_1+" + "+num_2);
                return num_1+num_2;
            case 2:
                System.out.println(num_1+" - "+num_2);
                return num_1-num_2 ;
            case 3:
                System.out.println(num_1+" * "+num_2);
                return num_1*num_2 ;
        }
        return 0 ;
    }
    public String ascii_art_kapcha(){
        String ans = "";
        Map<Integer, Command> artMap = new HashMap<>();
        for (int i = 0; i < 36; i++) {
            artMap.put( i, Command.values()[i]);
        }
        Random random = new Random();
        int randomNumber = random.nextInt(10) ;
        ans+=randomNumber;
        randomNumber = random.nextInt(26)+10;
        ans+=randomNumber;
        randomNumber = random.nextInt(10) ;
        ans+=randomNumber;
        randomNumber = random.nextInt(26)+10;
        ans+=randomNumber;
        return ans ;
    }

}
