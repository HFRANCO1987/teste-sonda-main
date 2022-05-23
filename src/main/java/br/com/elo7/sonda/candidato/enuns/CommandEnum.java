package br.com.elo7.sonda.candidato.enuns;

import br.com.elo7.sonda.candidato.model.Probe;

public enum CommandEnum {

    RIGHT("R"){
        @Override
        public void applyCommandToProbe(Probe probe) {
            DirectionEnum.valueOf(probe.getDirection().name()).turnProbe(probe, this);
        }
    },
    LEFT("L"){
        @Override
        public void applyCommandToProbe(Probe probe) {
            DirectionEnum.valueOf(probe.getDirection().name()).turnProbe(probe, this);
        }
    },
    MOVE("M"){
        @Override
        public void applyCommandToProbe(Probe probe) {
            DirectionEnum.valueOf(probe.getDirection().name()).moveProbeForward(probe);
        }
    };

    private final String command;

    CommandEnum(String command) {
        this.command = command;
    }

    public static CommandEnum getCommandEnum(String command) {
        for (CommandEnum commandEnum : CommandEnum.values()){
            if (command.equals(commandEnum.command)){
                return commandEnum;
            }
        }
        throw new IllegalArgumentException("Command invalid!");
    }

    public String getCommand() {
        return command;
    }

    public abstract void applyCommandToProbe(Probe probe);
}
