package org.ta4j.core.indicators.nate.helper;

import java.util.Optional;

public enum Period30m {
    A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9), K(10), L(11), M(12);

    Period30m(int i) {
    }

    public Optional<Period30m> next() {
        switch (this) {
            case A:
                return Optional.of(B);
            case B:
                return Optional.of(C);
            case C:
                return Optional.of(D);
            case D:
                return Optional.of(E);
            case E:
                return Optional.of(F);
            case F:
                return Optional.of(G);
            case G:
                return Optional.of(H);
            case H:
                return Optional.of(I);
            case I:
                return Optional.of(J);
            case J:
                return Optional.of(K);
            case K:
                return Optional.of(L);
            case L:
                return Optional.of(M);
            default:
                return Optional.empty();
        }
    }

    public Optional<Period30m> previous() {
        switch (this) {
            case B:
                return Optional.of(A);
            case C:
                return Optional.of(B);
            case D:
                return Optional.of(C);
            case E:
                return Optional.of(D);
            case F:
                return Optional.of(E);
            case G:
                return Optional.of(F);
            case H:
                return Optional.of(G);
            case I:
                return Optional.of(H);
            case J:
                return Optional.of(I);
            case K:
                return Optional.of(J);
            case L:
                return Optional.of(K);
            case M:
                return Optional.of(L);
            default:
                return Optional.empty();
        }
    }
}
