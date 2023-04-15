public class Fruits5AllTester {
    public static void main(String[] args) throws Exception {
        ProjectTester tester = new ProjectTesterImp(); //Instantiate your own ProjectTester instance here

        System.out.println("Start crawl...");
        long s = System.currentTimeMillis();

        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/fruits5/N-0.html");

        long e = System.currentTimeMillis();
        System.out.println("Crawl took: " + ((e-s)/1000.0) + "s");


        Fruits5OutgoingLinksTester.runTest(tester);
        Fruits5IncomingLinksTester.runTest(tester);
        Fruits5PageRanksTester.runTest(tester);
        Fruits5IDFTester.runTest(tester);
        Fruits5TFTester.runTest(tester);
        Fruits5TFIDFTester.runTest(tester);

        System.out.println("Start search...");
        long start = System.currentTimeMillis();

        Fruits5SearchTester.runTest(tester);

        long end = System.currentTimeMillis();
        System.out.println("Search took: " + ((end-start)/1000.0) + "s");

        System.out.println("Finished running all tests.");
    }
}
