package br.com.elo7.sonda.candidato.enuns;

import br.com.elo7.sonda.candidato.model.Probe;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DirectionEnum {

    NORTH('N'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.incrementAxisY();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            probe.newDirection(CommandEnum.LEFT.equals(commandEnum) ? DirectionEnum.WEST : DirectionEnum.EAST);
        }
    },

    EAST('E'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.incrementAxisX();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            probe.newDirection(CommandEnum.LEFT.equals(commandEnum) ? DirectionEnum.NORTH : DirectionEnum.SOUTH);
        }
    },

    SOUTH('S'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.subtractAxisY();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            probe.newDirection(CommandEnum.LEFT.equals(commandEnum) ? DirectionEnum.EAST : DirectionEnum.WEST);
        }
    },

    WEST('W'){
        @Override
        void moveProbeForward(Probe probe) {
            probe.subtractAxisX();
        };
        @Override
        void turnProbe(Probe probe, CommandEnum commandEnum) {
            probe.newDirection(CommandEnum.LEFT.equals(commandEnum) ? DirectionEnum.SOUTH : DirectionEnum.NORTH);
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
        throw new IllegalArgumentException("Direction invalid!");
    }

    @JsonValue
    public String getDirection() {
        return String.valueOf(direction);
    }

    abstract void moveProbeForward(Probe probe);
    abstract void turnProbe(Probe probe, CommandEnum commandEnum);

}

