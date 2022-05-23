package br.com.elo7.sonda.candidato.enuns;

import br.com.elo7.sonda.candidato.model.Probe;

public enum DirectionEnum {

    NORTH('N'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.incrementAxisY();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            if (CommandEnum.LEFT.equals(commandEnum)){
                probe.newDirection(DirectionEnum.WEST);
            }else{
                probe.newDirection(DirectionEnum.EAST);
            }
        }
    },

    EAST('E'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.incrementAxisX();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            if (CommandEnum.LEFT.equals(commandEnum)){
                probe.newDirection(DirectionEnum.NORTH);
            }else{
                probe.newDirection(DirectionEnum.SOUTH);
            }

        }
    },

    SOUTH('S'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.subtractAxisY();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            if (CommandEnum.LEFT.equals(commandEnum)){
                probe.newDirection(DirectionEnum.EAST);
            }else{
                probe.newDirection(DirectionEnum.WEST);
            }
        }
    },

    WEST('W'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.subtractAxisX();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            if (CommandEnum.LEFT.equals(commandEnum)){
                probe.newDirection(DirectionEnum.SOUTH);
            }else{
                probe.newDirection(DirectionEnum.NORTH);
            }
        }
    };

    private final char direction;

    DirectionEnum(char direction) {
        this.direction = direction;
    }

    public static DirectionEnum getDirectionEnum(String direction) {
        for (DirectionEnum directionEnum : DirectionEnum.values()){
            if (direction.equals(String.valueOf(directionEnum.direction))){
                return directionEnum;
            }
        }
        return null;
    }

    public char getDirection() {
        return direction;
    }

    abstract void moveProbeForward(Probe probe);
    abstract void turnProbe(Probe probe, CommandEnum commandEnum);

}

