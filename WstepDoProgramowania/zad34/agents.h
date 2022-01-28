struct agent
{
    int x;
    int y;
};
struct agent newagent(int x, int y);

void east(struct agent *a);
void north(struct agent *a);
void south(struct agent *a);
void west(struct agent *a);

double dist(struct agent a1, struct agent a2);
