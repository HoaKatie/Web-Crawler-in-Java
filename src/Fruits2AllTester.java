public class Fruits2AllTester {
    public static void main(String[] args) throws Exception {
        ProjectTester tester = new ProjectTesterImp(); //Instantiate your own ProjectTester instance here


        System.out.println("Start crawl...");
        long s = System.currentTimeMillis();

        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/fruits2/N-0.html");

        long e = System.currentTimeMillis();
        System.out.println("Crawl took: " + ((e-s)/1000.0) + "s");


        Fruits2OutgoingLinksTester.runTest(tester);
        Fruits2IncomingLinksTester.runTest(tester);
        Fruits2PageRanksTester.runTest(tester);
        Fruits2IDFTester.runTest(tester);
        Fruits2TFTester.runTest(tester);
        Fruits2TFIDFTester.runTest(tester);

        System.out.println("Start search...");
        long start = System.currentTimeMillis();

        Fruits2SearchTester.runTest(tester);

        long end = System.currentTimeMillis();
        System.out.println("Crawl took: " + ((end-start)/1000.0) + "s");

        System.out.println("Finished running all tests.");
    }
}
