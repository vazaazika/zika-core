package br.les.opus.gamification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.auth.core.domain.User;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.repositories.InviteRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.gamification.repositories.TaskRepository;

/**
 * Created by andersonjso on 8/20/18.
 */

@Service
public class InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private TaskGroupService taskGroupService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PerformedTaskService performedTaskService;

    @Autowired
    private PlayerRepository playerRepository;

    public void giveXPToPlayerForInvite(Player player) {
        Task task = taskRepository.findOne(new Long(8)); //8 is the id for invite confirmation task
        PerformedTask performedTask = performedTaskService.register(task, player);

        taskGroupService.trackProgress(performedTask);
    }

    public void sendXPForInvite(String invitationId, User newObject) {
        Player playerWhoSent = inviteRepository.findUserByInviteId(invitationId);
        Player playerWhoReceived = playerRepository.findOne(newObject.getId());

        giveXPToPlayerForInvite(playerWhoSent);
        giveXPToPlayerForInvite(playerWhoReceived);
    }
}
