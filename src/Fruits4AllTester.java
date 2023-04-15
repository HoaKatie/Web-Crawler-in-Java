public class Fruits4AllTester {
    public static void main(String[] args) throws Exception {
        ProjectTester tester = new ProjectTesterImp(); //Instantiate your own ProjectTester instance here

        System.out.println("Start crawl...");
        long s = System.currentTimeMillis();

        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/fruits4/N-0.html");

        long e = System.currentTimeMillis();
        System.out.println("Crawl took: " + ((e-s)/1000.0) + "s");



        Fruits4OutgoingLinksTester.runTest(tester);
        Fruits4IncomingLinksTester.runTest(tester);
        Fruits4PageRanksTester.runTest(tester);
        Fruits4IDFTester.runTest(tester);
        Fruits4TFTester.runTest(tester);
        Fruits4TFIDFTester.runTest(tester);

        System.out.println("Start search...");
        long start = System.currentTimeMillis();

        Fruits4SearchTester.runTest(tester);

        long end = System.currentTimeMillis();
        System.out.println("Search took: " + ((end-start)/1000.0) + "s");

        System.out.println("Finished running all tests.");
    }
}
