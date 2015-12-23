package org.basic.pattern.behavioral.State;

/**
 * <pre>
 *
 * </pre>
 */
public class TVContext implements TVState {

    private TVState state;

    public TVState getState() {
        return state;
    }

    public void setState(TVState state) {
        this.state = state;
    }

    @Override
    public void doAction() {
        this.state.doAction();
    }

}
