public class Fruits1AllTester {
    public static void main(String[] args) throws Exception {
        ProjectTester tester = new ProjectTesterImp(); //Instantiate your own ProjectTester instance here

        System.out.println("Start crawl...");
        long s = System.currentTimeMillis();

        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html");

        long e = System.currentTimeMillis();
        System.out.println("Crawl took: " + ((e-s)/1000.0) + "s");



        System.out.println("Start search...");
        long start = System.currentTimeMillis();

        Fruits1OutgoingLinksTester.runTest(tester);
        Fruits1IncomingLinksTester.runTest(tester);
        Fruits1PageRanksTester.runTest(tester);
        Fruits1IDFTester.runTest(tester);
        Fruits1TFTester.runTest(tester);
        Fruits1TFIDFTester.runTest(tester);
        Fruits1SearchTester.runTest(tester);

        long end = System.currentTimeMillis();
        System.out.println("Search took: " + ((end-start)/1000.0) + "s");

        System.out.println("Finished running all tests.");
    }
}
