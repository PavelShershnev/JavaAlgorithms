package algorithms;

import org.junit.jupiter.api.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Threads(1)
@Timeout(time = 10)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time=10)
@Measurement(iterations =3, time =10)
public class SubstringSearchAlgorithmsTest {
        final String INPUT_DEFAULT = "What does the brain matter";
        final String INPUT_MANYMATCHES = "What does the brain matter does the the does the";
        final String PATTERN_THE = "the";
        final String PATTERN_ABRA = "abra";
        double memoryBefore =0;

        @BeforeEach
        public void init(TestInfo testInfo){
            String methodName = testInfo.getTestMethod().orElseThrow().getName();
            System.out.println("Method: "+methodName);
            System.gc();
            Runtime runtime = Runtime.getRuntime();
            memoryBefore = (double)(runtime.totalMemory() - runtime.freeMemory());
        }

        @AfterEach
        public void printMemoryUsage (){
            Runtime runtime = Runtime.getRuntime();
            double memoryAfter = (double)(runtime.totalMemory() - runtime.freeMemory());
            System.out.printf("\nMemory used: %.2f KBytes", (memoryAfter-memoryBefore)/1024);
        }

        @Test
        public void runBenchmarks() throws Exception{
            Options options = new OptionsBuilder()
                    .build();
            new Runner(options).run();
        }

        @Benchmark
        @Test
        public void directSearchOneMatches () {
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.directSearch(INPUT_DEFAULT, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void directSearchNoneMatches (){
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.directSearch(INPUT_DEFAULT, PATTERN_ABRA));
        }

        @Benchmark
        @Test
        public void directSearchManyMatches (){
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.directSearch(INPUT_MANYMATCHES, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void kmpSearchOneMatches () {
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.kmpSearch(INPUT_DEFAULT, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void kmpSearchNoneMatches (){
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.kmpSearch(INPUT_DEFAULT, PATTERN_ABRA));
        }

        @Benchmark
        @Test
        public void kmpSearchManyMatches (){
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.kmpSearch(INPUT_MANYMATCHES, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void bmSearchManyMatches (){
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.bmSearch(INPUT_MANYMATCHES, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void bmSearchOneMatches () {
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.bmSearch(INPUT_DEFAULT, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void bmSearchNoneMatches (){
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.bmSearch(INPUT_DEFAULT, PATTERN_ABRA));
        }


        @Benchmark
        @Test
        public void rkSearchManyMatches (){
            Assertions.assertEquals(List.of(10,32,36,45),SubstringSearchAlgorithms.rkSearch(INPUT_MANYMATCHES, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void rkSearchOneMatches () {
            Assertions.assertEquals(List.of(10),SubstringSearchAlgorithms.rkSearch(INPUT_DEFAULT, PATTERN_THE));
        }

        @Benchmark
        @Test
        public void rkSearchNoneMatches (){
            Assertions.assertEquals(List.of(),SubstringSearchAlgorithms.rkSearch(INPUT_DEFAULT, PATTERN_ABRA));
        }

}