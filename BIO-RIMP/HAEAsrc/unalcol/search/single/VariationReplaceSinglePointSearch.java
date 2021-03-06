/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.search.single;

import unalcol.search.Solution;
import unalcol.search.Goal;
import unalcol.search.space.Space;
import unalcol.search.space.ArityOne;

/**
 *
 * @author jgomez
 */
public class VariationReplaceSinglePointSearch<T> extends SinglePointSearch<T> {
    protected ArityOne<T> variation;
    protected Replacement<T> replace;
    
    public VariationReplaceSinglePointSearch( ArityOne<T> variation, Replacement<T> replace ){
        super();
        this.variation = variation;
        this.replace = replace;
    }
    
    @Override
    public void init(){}

    @Override
    public Solution<T> apply(Solution<T> x, Space<T> space, Goal<T> goal){
        // Check if non stationary
        x.quality(goal);
        Solution<T> y = new Solution<T>(variation.apply(space, x.value()),goal);
        variation.adapt(y.quality()-x.quality());
        return replace.apply(x, y);
    }    
}