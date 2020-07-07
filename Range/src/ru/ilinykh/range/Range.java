package ru.ilinykh.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double x) {
        return (x >= from) && (x <= to);
    }

    public Range getIntersection(Range x) {
        double from1 = from;
        double to1 = to;
        double from2 = x.getFrom();
        double to2 = x.getTo();

        double intersectionStart;
        double intersectionEnd;

        if (from1 >= from2) {
            if (from1 >= to2) {
                return null;
            }

            intersectionStart = from1;
            intersectionEnd = Math.min(to2, to1);
        } else {
            if (from2 >= to1) {
                return null;
            }

            intersectionStart = from2;
            intersectionEnd = Math.min(to1, to2);
        }

        return new Range(intersectionStart, intersectionEnd);
    }

    public Range[] getUnion(Range x) {
        double from1 = from;
        double to1 = to;
        double from2 = x.getFrom();
        double to2 = x.getTo();

        double unionStart1;
        double unionEnd1;
        double unionStart2 = 0;
        double unionEnd2 = 0;

        int segmentsCount;

        if (from1 >= from2) {
            if (from1 > to2) {
                unionStart1 = from2;
                unionEnd1 = to2;
                unionStart2 = from1;
                unionEnd2 = to1;

                segmentsCount = 2;
            } else if (to2 <= to1) {
                unionStart1 = from2;
                unionEnd1 = to1;

                segmentsCount = 1;
            } else {
                unionStart1 = from2;
                unionEnd1 = to2;

                segmentsCount = 1;
            }
        } else {
            if (from2 > to1) {
                unionStart1 = from1;
                unionEnd1 = to1;
                unionStart2 = from2;
                unionEnd2 = to2;

                segmentsCount = 2;
            } else if (to2 >= to1) {
                unionStart1 = from1;
                unionEnd1 = to2;

                segmentsCount = 1;
            } else {
                unionStart1 = from1;
                unionEnd1 = to1;

                segmentsCount = 1;
            }
        }

        Range[] unions = new Range[segmentsCount];
        unions[0] = new Range(unionStart1, unionEnd1);

        if (segmentsCount == 2) {
            unions[1] = new Range(unionStart2, unionEnd2);
        }

        return unions;
    }

    public Range[] getDifference(Range x) {
        double from1 = from;
        double to1 = to;
        double from2 = x.getFrom();
        double to2 = x.getTo();

        if ((from2 <= from1) && (to2 >= to1)) {
            return null;
        }

        double differenceStart1;
        double differenceEnd1;
        double differenceStart2 = 0;
        double differenceEnd2 = 0;

        int segmentsCount;

        if (from1 >= from2) {
            differenceEnd1 = to1;
            segmentsCount = 1;

            differenceStart1 = Math.max(from1, to2);

        } else {
            if (from2 >= to1) {
                differenceStart1=from1;
                differenceEnd1 = to1;

                segmentsCount = 1;
            } else if (to2 >= to1) {
                differenceStart1=from1;
                differenceEnd1 = from2;

                segmentsCount = 1;
            } else {
                differenceStart1=from1;
                differenceEnd1 = from2;
                differenceStart2=to2;
                differenceEnd2 = to1;

                segmentsCount = 2;
            }
        }

        Range[] unions = new Range[segmentsCount];
        unions[0] = new Range(differenceStart1, differenceEnd1);

        if (segmentsCount == 2) {
            unions[1] = new Range(differenceStart2, differenceEnd2);
        }

        return unions;
    }
}