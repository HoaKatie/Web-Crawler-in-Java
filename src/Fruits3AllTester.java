public class Fruits3AllTester {
    public static void main(String[] args) throws Exception {
        ProjectTester tester = new ProjectTesterImp(); //Instantiate your own ProjectTester instance here

        System.out.println("Start crawl...");
        long s = System.currentTimeMillis();

        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/fruits3/N-0.html");

        long e = System.currentTimeMillis();
        System.out.println("Crawl took: " + ((e-s)/1000.0) + "s");



        Fruits3OutgoingLinksTester.runTest(tester);
        Fruits3IncomingLinksTester.runTest(tester);
        Fruits3PageRanksTester.runTest(tester);
        Fruits3IDFTester.runTest(tester);
        Fruits3TFTester.runTest(tester);
        Fruits3TFIDFTester.runTest(tester);

        System.out.println("Start search...");
        long start = System.currentTimeMillis();

        Fruits3SearchTester.runTest(tester);

        long end = System.currentTimeMillis();
        System.out.println("Search took: " + ((end-start)/1000.0) + "s");

        System.out.println("Finished running all tests.");
    }
}
