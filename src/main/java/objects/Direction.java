package objects;

// Simple enum for directions
public enum Direction {
    NORTH {
        @Override
        public Point move(Point p) { return new Point(p.x, p.y - 1); }

        @Override
        public Direction turnRight() { return EAST; }

        @Override
        public Direction turnLeft() {
            return WEST;
        }
    },
    EAST {
        @Override
        public Point move(Point p) { return new Point(p.x + 1, p.y); }

        @Override
        public Direction turnRight() { return SOUTH; }

        @Override
        public Direction turnLeft() {
            return NORTH;
        }
    },
    SOUTH {
        @Override
        public Point move(Point p) { return new Point(p.x, p.y + 1); }

        @Override
        public Direction turnRight() { return WEST; }

        @Override
        public Direction turnLeft() {
            return EAST;
        }
    },
    WEST {
        @Override
        public Point move(Point p) { return new Point(p.x - 1, p.y); }

        @Override
        public Direction turnRight() { return NORTH; }

        @Override
        public Direction turnLeft() {
            return SOUTH;
        }
    };

    abstract Point move(Point p);
    public abstract Direction turnRight();
    public abstract Direction turnLeft();
}
