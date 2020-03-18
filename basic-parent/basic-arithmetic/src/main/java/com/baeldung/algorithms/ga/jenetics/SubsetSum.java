package com.baeldung.algorithms.ga.jenetics;

import static java.util.Objects.requireNonNull;

import java.util.Random;
import java.util.function.Function;

import io.jenetics.EnumGene;
import io.jenetics.Mutator;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.Phenotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.engine.Problem;
import io.jenetics.prngine.LCG64ShiftRandom;
import io.jenetics.util.ISeq;

public class SubsetSum implements Problem<ISeq<Integer>, EnumGene<Integer>, Integer> {

    private ISeq<Integer> basicSet;
    private int size;

    public SubsetSum(ISeq<Integer> basicSet, int size) {
        this.basicSet = requireNonNull(basicSet);
        this.size = size;
    }

    @Override
    public Function<ISeq<Integer>, Integer> fitness() {
        return subset -> Math.abs(subset.stream()
            .mapToInt(Integer::intValue)
            .sum());
    }

    @Override
    public Codec<ISeq<Integer>, EnumGene<Integer>> codec() {
        return Codecs.ofSubSet(basicSet, size);
    }

    public static SubsetSum of(int n, int k, Random random) {
        return new SubsetSum(random.doubles()
            .limit(n)
            .mapToObj(d -> (int) ((d - 0.5) * n))
            .collect(ISeq.toISeq()), k);
    }

    public static void main(String[] args) {
        SubsetSum problem = of(500, 15, new LCG64ShiftRandom(101010));

        Engine<EnumGene<Integer>, Integer> engine = Engine.builder(problem)
            .minimizing()
            .maximalPhenotypeAge(5)
            .alterers(new PartiallyMatchedCrossover<>(0.4), new Mutator<>(0.3))
            .build();

        Phenotype<EnumGene<Integer>, Integer> result = engine.stream()
            .limit(Limits.bySteadyFitness(55))
            .collect(EvolutionResult.toBestPhenotype());

        System.out.print(result);
    }

}