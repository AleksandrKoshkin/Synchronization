import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int sum = 0;
                for (int j = 0; j < route.length(); j++) {
                    char r = 'R';
                    if (r == route.charAt(j)) {
                        sum++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (!sizeToFreq.containsKey(sum)) {
                        int replay = 1;
                        sizeToFreq.put(sum, replay);
                    } else {
                        int replay = sizeToFreq.remove(sum);
                        replay++;
                        sizeToFreq.put(sum, replay);
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        int max = 0;
        int maxKey = 0;
        for (int i = 0; i < 100; i++) {
            if (sizeToFreq.containsKey(i)) {
                if (sizeToFreq.get(i) > max) {
                    max = sizeToFreq.get(i);
                    maxKey = i;
                }
            }
        }
        System.out.println("Самое частое количество повторений " + maxKey + "(встретилось " + max + " раз)");
        System.out.println("Другие размеры:");
        for (int i = 0; i < 100; i++) {
            if (sizeToFreq.containsKey(i)) {
                System.out.println(i + " " + sizeToFreq.get(i) + " раз");
            }
        }
    }
}