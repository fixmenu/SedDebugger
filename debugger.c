#define DEBUG(fmt, ...) fprintf(stderr, "DEBUG [%s:%s:%d] " fmt, __FILE__, __func__, __LINE__, ## __VA_ARGS__)
#define DBG(fmt, ...) fprintf(stdout, "[DBG]:[%d] " fmt, line_number, ## __VA_ARGS__)

#define DBG_OUT(fmt,...) fprintf(stdout, "[DBG_OUTPUT]:" fmt, ## __VA_ARGS__)

#include "sed.h"
#include "debugger.h"

#include <stdio.h>

/* these are defined in sed source: */
static int line_number;
/* --- EOP */

static volatile int breakpoint_wanted = 0;

enum { EXEC_CONT, EXEC_NEXT_CMD, EXEC_NEXT_LINE };

static int execution_mode = EXEC_NEXT_CMD;
static int bp_line_number = -1;

void set_line_number(int line_no) {
    line_number = line_no;
}

void block_if_paused( FILE *in, int line_number ) {
    //DBG("THIS IS SED DEBUGGER\n");
    int ch;
    //while (breakpoint_wanted) {
    /* 
     * Adding break points required stack data structure, the user should specify lines to be debugged
     * All changes should be updated and inform to the user
     * */
    //}

    if (execution_mode == EXEC_CONT) { return; }
    if (execution_mode == EXEC_NEXT_LINE) {
        if (bp_line_number != line_number)
            return;
    }

    do {
        ch = fgetc ( in );
        //DBG("GET: %c\n", ch);
    } while (!strchr("cns", ch));

    if (ch == 'n') {
        execution_mode = EXEC_NEXT_LINE;
        bp_line_number = line_number + 1;
    } else if (ch == 's') {
        execution_mode = EXEC_NEXT_CMD;
    } else if (ch == 'c') {
        execution_mode = EXEC_CONT;
    }
    clearerr ( in );
}


void debug(struct sed_cmd *cur_cmd)
{

    DBG("COMMAND %c\n",cur_cmd->cmd);
    if (cur_cmd->cmd == 's')
    {
        DBG("G:%d P:%d%d E:%d MAX:%d NUMB:%d OUT:%s FP:%p RX:%s RXF:%d "
                "RXS:%zu NSUB:%d\n", 
                cur_cmd->x.cmd_subst->global,
                (cur_cmd->x.cmd_subst->print & 2) == 2,
                (cur_cmd->x.cmd_subst->print & 1) == 1,
                cur_cmd->x.cmd_subst->eval,
                cur_cmd->x.cmd_subst->max_id,
                cur_cmd->x.cmd_subst->numb,
                cur_cmd->x.cmd_subst->outf ? cur_cmd->x.cmd_subst->outf->name : "None",
                cur_cmd->x.cmd_subst->outf ? cur_cmd->x.cmd_subst->outf->fp : 0,
                cur_cmd->x.cmd_subst->regx->re,
                cur_cmd->x.cmd_subst->regx->flags,
                cur_cmd->x.cmd_subst->regx->sz,
                cur_cmd->x.cmd_subst->regx->pattern.re_nsub);
    }

    if (cur_cmd->cmd == 'a')
    {  
        printf("Text:%s Length:%zu\n",
                cur_cmd->x.cmd_txt.text,
                cur_cmd->x.cmd_txt.text_length);
    }

    if (cur_cmd->cmd == 'y')
    {
        DBG("Translate:%i\n", cur_cmd->x.translate);
        char **trans = cur_cmd->x.translatemb;
        // `i' indicate i-th translate pair.
        for (int i = 0; trans[2*i] != NULL; i++) {
            DBG("%s -> %s\n", trans[2 * i], trans[2 * i + 1]);
        }
        //for (char i = 1; i > 0 ; i++) {
        //  printf("%c -> %u\n", i, cur_cmd->x.translate[i]);
        //}
    }
    if (cur_cmd->cmd == 't')
    {
        DBG("INDEX: %d\n", cur_cmd->x.jump_index);
    }
}

void match_found(struct re_registers *regs,  int start, int offset, int matched) {
      DBG("MATCH_S: S %d O %d E %d L %d\n", start, offset, regs->end[0], matched);
      DBG("REGS: %d\n", regs->num_regs);
      for (int i = 0; i < regs->num_regs; i++) {
          DBG("\tREG %d %d\n", regs->start[i], regs->end[i]);
      }
}

void programPause () {
    DBG("Eneter [Enter] to continue");
    fflush ( stdout );
    getchar();
}
