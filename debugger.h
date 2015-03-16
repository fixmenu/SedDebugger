#ifndef DEBUGGER_H_
#define DEBUGGER_H_
#include <stdio.h>

void match_found(struct re_registers *regs,  int start, int offset, int matched);
void block_if_paused(FILE*, int);
void debug (struct sed_cmd *);
void set_line_number(int line_no);

#endif
