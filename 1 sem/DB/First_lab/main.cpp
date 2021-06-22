#include <cstdio>
#include <clocale>
#include <cstring>

# define KEY_LEN 11
# define NAME_LEN 201
# define ADRESS_LEN 201
# define DELETED_LEN 11

int k_T = 1;
int k_I = 1;
int k_P = 1;

int realtor_id = 1;
int house_id = 1;
char r_id[KEY_LEN];

typedef struct realtor
{
    char keyT[KEY_LEN];
    char nameT[NAME_LEN];
    int q_houses;
    int flag;
    int link;
    int col_link;
} realtorFL;

typedef struct house
{
    char keyP[KEY_LEN];
    char adressP[ADRESS_LEN];
    int cost;
    int flag;
    int link;
} house;

typedef struct realtor_ind
{
    char key[KEY_LEN];
    int adress;
    int flag;
} realtorInd;

void insert_R()
{
    sprintf(r_id, "%d", realtor_id);

    printf("Код риелтора: ");
    FILE* fl;
    char name[] = "realtor.txt";
    int s[DELETED_LEN];
    fl = fopen(name, "rb+");
    realtorFL t;

    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorFL r;
    fseek(fl, (long)(sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    int k = 0;
    while (k < k_T)
    {
        k++;
        fread(&r, sizeof(realtorFL), 1, fl);
        if (strcmp(r.keyT, key_) == 0)
        {
            k = 0;
            printf("Риелтор с данным кодом существует. \nВведите другой код риелтора: ");
            scanf("%10s", key_);
        }
    }

    strcpy((t.keyT), key_);

    printf("Имя риелтора: ");
    scanf("%200s", &(t.nameT));

    t.q_houses = 0;
    t.flag = 1;
    t.link = -1;
    t.col_link = 0;

    fseek(fl, 0, SEEK_SET);
    fread(&s, sizeof(int), DELETED_LEN, fl);
    int l = -1;
    if (s[0] != 0 && s[0] < k_T)
    {
        l = s[0];
        int i = 1;
        while (i < DELETED_LEN)
        {
            s[i - 1] = s[i];
            i++;
        }
    }
    else
    {
        l = k_T;
        k_T++;
    }

    fseek(fl, (long)l * sizeof(realtorFL) + (long)DELETED_LEN * sizeof(int), SEEK_SET);
    fwrite(&t, sizeof(realtorFL), 1, fl);
    FILE* ind;
    char name1[] = "realtorInd.txt";
    ind = fopen(name1, "rb+");
    realtorInd t_I;
    int h = 0;
    while (h < KEY_LEN)
    {
        t_I.key[h] = t.keyT[h];
        h++;
    }
    t_I.adress = l;
    t_I.flag = 1;
    fseek(ind, (long)k_I * sizeof(realtorInd), SEEK_SET);
    k_I++;
    fwrite(&t_I, sizeof(realtorInd), 1, ind);
    printf("Success\n");
    fclose(fl);
    fclose(ind);
}

realtorFL get_R()
{
    printf("Ввести код риелтора: ");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd t;
    int l;
    while (!feof(ind))
    {
        fread(&t, sizeof(realtorInd), 1, ind);
        if (strcmp(t.key, key_) == 0)
        {
            l = t.adress;
            break;
        }
    }
    if (strcmp(t.key, key_) != 0)
    {
        realtorFL f;
        f.link = -1;
        f.flag = -1;
        f.nameT[0] = '-1';
        fclose(ind);
        return f;
    }
    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb");
    realtorFL t_fl;
    fseek(fl, (long)(l * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&t_fl, sizeof(realtorFL), 1, fl);
    fclose(fl);
    fclose(ind);
    realtorFL f;
    f.link = -1;
    f.nameT[0] = '-1';
    f.flag = -1;
    if (t_fl.flag == -1) { printf("Удалено\n"); return f; }
    else
        return t_fl;
}

void update_R()
{
    printf("Ввести ключ риелтора: ");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd t;
    int l;
    while (!feof(ind))
    {
        fread(&t, sizeof(realtorInd), 1, ind);
        if (strcmp(t.key, key_) == 0)
        {
            l = t.adress;
            break;
        }
    }
    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb+");
    realtorFL t_fl;
    fseek(fl, (long)(l * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&t_fl, sizeof(realtorFL), 1, fl);
    printf("Новое имя риелтора: ");
    char n[NAME_LEN];
    scanf("%s", n);
    strcpy(t_fl.nameT, n);

    fseek(fl, (long)(l * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fwrite(&t_fl, sizeof(realtorFL), 1, fl);
    printf("Success\n");
    fclose(fl);
    fclose(ind);
}

void del_R()
{
    printf("Введите ключ риелтора:\n");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd t;
    int c = 0;
    int ll;
    while (!feof(ind))
    {
        c++;
        fread(&t, sizeof(realtorInd), 1, ind);
        if (strcmp(t.key, key_) == 0)
        {
            ll = t.adress;
            break;
        }
    }
    if (strcmp(t.key, key_) != 0)
    {
        printf("Риелтора с таким ключем не существует\n");
        fclose(ind);
        return;
    }
    t.flag = -1;
    fseek(ind, (long)(c * sizeof(realtorInd)), SEEK_SET);
    fwrite(&t, sizeof(realtorInd), 1, ind);

    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb+");
    realtorFL r_fl;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&r_fl, sizeof(realtorFL), 1, fl);
    r_fl.flag = -1;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fwrite(&r_fl, sizeof(realtorFL), 1, fl);
    int s[DELETED_LEN];
    fseek(fl, 0, SEEK_SET);
    fread(&s, sizeof(int), DELETED_LEN, fl);

    int i = 0;
    while (s[i] > 0 && s[i] <= k_T) i++;
    s[i] = ll;
    fseek(fl, 0, SEEK_SET);
    fwrite(&s, sizeof(int), DELETED_LEN, fl);
    int ln = r_fl.link;
    FILE* hous;
    char name2[] = "houses.txt";
    hous = fopen(name2, "rb+");
    house hs;
    while (ln != -1)
    {
        fseek(hous, (long)(ln * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fread(&hs, sizeof(house), 1, hous);

        hs.flag = -1;
        fseek(hous, (long)(ln * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&hs, sizeof(house), 1, hous);
        fseek(hous, 0, SEEK_SET);
        fread(&s, sizeof(int), DELETED_LEN, hous);
        i = 0;
        while (s[i] > 0 && s[i] <= k_P) i++;
        s[i] = ln;
        ln = hs.link;
        fseek(hous, 0, SEEK_SET);
        fwrite(&s, sizeof(int), DELETED_LEN, hous);
    }
    r_fl.link = -1;
    printf("Success\n");
    fclose(fl);
    fclose(hous);
    fclose(ind);
}

void insert_H()
{
    printf("Введите ключ риелтора:\n");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd r;
    int ll;
    while (!feof(ind))
    {
        fread(&r, sizeof(realtorInd), 1, ind);
        if (strcmp(r.key, key_) == 0)
        {
            ll = r.adress;
            break;
        }
    }
    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb+");
    realtorFL t_fl;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&t_fl, sizeof(realtorFL), 1, fl);
    t_fl.col_link++;
    t_fl.q_houses++;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fwrite(&t_fl, sizeof(realtorFL), 1, fl);

    house p;
    FILE* hss;
    char name2[] = "houses.txt";
    hss = fopen(name2, "rb+");
    printf("Ввести ключ дома: ");

    char key_h[KEY_LEN];
    scanf("%10s", key_h);
    fseek(hss, (long)(sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
    int k = 0;
    while (k < k_P)
    {
        k++;
        fread(&p, sizeof(house), 1, hss);
        if (strcmp(p.keyP, key_h) == 0)
        {
            k = 0;
            printf("Дом с данным кодом существует. \nВведите другой код дома: ");
            scanf("%10s", key_h);
        }
    }
    strcpy((p.keyP), key_h);

    printf("Ввести адресс: ");
    scanf("%200s", &(p.adressP));
    printf("Ввести цену: ");
    scanf("%d", &(p.cost));

    p.link = -1;
    p.flag = 1;


    int s[DELETED_LEN];
    fseek(hss, 0, SEEK_SET);
    fread(&s, sizeof(int), DELETED_LEN, hss);
    int l = -1;
    if (s[0] != 0 && s[0] < k_P)
    {
        l = s[0];
        int i = 1;
        while (i < DELETED_LEN)
        {
            s[i - 1] = s[i];
            i++;
        }
    }
    else
    {
        l = k_P;
        k_P++;
    }
    if (t_fl.link == -1)
    {
        t_fl.link = l;
        fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&t_fl, sizeof(realtorFL), 1, fl);

    }
    else
    {
        int ln = t_fl.link;
        int ln_ = t_fl.link;
        house pr;
        while (ln != -1)
        {
            fseek(hss, (long)(ln * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
            fread(&pr, sizeof(house), 1, hss);
            ln_ = ln;
            ln = pr.link;
        }
        pr.link = l;
        fseek(hss, (long)(ln_ * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&pr, sizeof(house), 1, hss);
    }
    fseek(hss, (long)(l * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fwrite(&p, sizeof(house), 1, hss);
    printf("Success\n");
    fclose(hss);
    fclose(fl);
    fclose(ind);
}

house get_H()
{

    printf("Ввести ключ риелтора:\n");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd r;
    int ll;
    while (!feof(ind))
    {
        fread(&r, sizeof(realtorInd), 1, ind);
        if (strcmp(r.key, key_) == 0)
        {
            ll = r.adress;
            break;
        }
    }
    if (ll != r.adress)
    {
        house nl;
        nl.adressP[0] = '-1';
        nl.cost = -1;
        nl.link = -1;
        fclose(ind);
        return nl;
    }
    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb+");
    realtorFL t_fl;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&t_fl, sizeof(realtorFL), 1, fl);
    printf("Ввести ключ дома:\n");
    char key_1[KEY_LEN];
    scanf("%10s", key_1);
    FILE* perf;
    char name2[] = "houses.txt";
    perf = fopen(name2, "rb+");
    house h;
    int ln = t_fl.link;
    fseek(perf, (long)(ln * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&h, sizeof(house), 1, perf);
    ln = h.link;
    int a = 0;
    while ((strcmp(h.keyP, key_1) != 0 && ln != -1) && a < k_P )
    {
        a++;
        fseek(perf, (long)(ln * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fread(&h, sizeof(house), 1, perf);
        ln = h.link;
        if (strcmp(h.keyP, key_1) == 0) break;
    }
    fclose(fl);
    fclose(ind);
    fclose(perf);
    house nl;
    nl.adressP[0] = '-1';
    nl.cost = -1;
    nl.link = -1;
    if (h.flag == -1 || strcmp(h.keyP, key_1) != 0) { printf("Удалено\n"); return nl; }
    else
        return h;
}

void update_H()
{
    printf("Ввести ключ риелтора:\n");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd t;
    int ll;
    while (!feof(ind))
    {
        fread(&t, sizeof(realtorInd), 1, ind);
        if (strcmp(t.key, key_) == 0)
        {
            ll = t.adress;
            break;
        }
    }
    if (strcmp(t.key, key_) != 0)
    {
        printf("Риелтора с таким ключем не существует\n");
        fclose(ind);
        return;
    }
    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb+");
    realtorFL r_fl;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&r_fl, sizeof(realtorFL), 1, fl);
    int pos_p = r_fl.link;
    printf("Ввести ключ дома:\n");
    char key_1[KEY_LEN];
    scanf("%10s", key_1);
    FILE* hous;
    char name2[] = "houses.txt";
    hous = fopen(name2, "rb+");
    house h;
    fseek(hous, (long)(r_fl.link * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&h, sizeof(house), 1, hous);
    while (strcmp(h.keyP, key_1) != 0 && !feof(hous))
    {
        pos_p = h.link;
        fseek(hous, (long)(h.link * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fread(&h, sizeof(house), 1, hous);
    }
    printf("Какое поле меняем - address / cost? \n");
    char pp[100];
    scanf("%s", pp);
    if (pp[0] == 'a' && pp[1] == 'd')
    {
        char n[NAME_LEN];
        scanf("%s", n);
        strcpy(h.adressP, n);
    }
    if (pp[0] == 'c')
    {
        int y;
        scanf("%d", &y);
        h.cost = y;
    }
    fseek(hous, (long)(pos_p * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fwrite(&h, sizeof(house), 1, hous);
    printf("Success\n");
    fclose(hous);
    fclose(fl);
    fclose(ind);
}

void del_H()
{
    printf("Ввести ключ риелтора:\n");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd r;
    int ll;
    while (!feof(ind))
    {
        fread(&r, sizeof(realtorInd), 1, ind);
        if (strcmp(r.key, key_) == 0)
        {
            ll = r.adress;
            break;
        }
    }
    if (strcmp(r.key, key_) != 0)
    {
        printf("Риелтора с таким ключем не существует");
        fclose(ind);
        return;
    }
    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb+");
    realtorFL r_fl;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&r_fl, sizeof(realtorFL), 1, fl);
    int pos_p = r_fl.link;

    printf("Ввести ключ дома:\n");
    char key_1[KEY_LEN];
    scanf("%10s", key_1);
    FILE* hous;
    char name2[] = "houses.txt";
    hous = fopen(name2, "rb+");
    house h;
    house h_prev;
    fseek(hous, (long)(r_fl.link * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&h, sizeof(house), 1, hous);

    if (r_fl.col_link == 1)
    {

        r_fl.col_link--;
        r_fl.link = -1;

        fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&r_fl, sizeof(realtorFL), 1, fl);

        h.flag = -1;
        fseek(hous, (long)(pos_p * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&h, sizeof(house), 1, hous);
        int s[DELETED_LEN];
        fseek(hous, 0, SEEK_SET);
        fread(&s, sizeof(int), DELETED_LEN, hous);
        int i = 0;
        while (s[i] > 0 && s[i] <= k_P) i++;
        s[i] = pos_p;
        fseek(hous, 0, SEEK_SET);
        fwrite(&s, sizeof(int), DELETED_LEN, hous);
    }
    else
    if (r_fl.col_link == 2 && strcmp(h.keyP, key_1) == 0)
    {
        r_fl.link = h.link;
        r_fl.col_link--;
        r_fl.q_houses--;
        fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&r_fl, sizeof(realtorFL), 1, fl);
        h.flag = -1;
        fseek(hous, (long)(pos_p * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&h, sizeof(house), 1, hous);
        int s[DELETED_LEN];
        fseek(hous, 0, SEEK_SET);
        fread(&s, sizeof(int), DELETED_LEN, hous);
        int i = 0;
        while (s[i] > 0 && s[i] <= k_P) i++;
        s[i] = pos_p;
        fseek(hous, 0, SEEK_SET);
        fwrite(&s, sizeof(int), DELETED_LEN, hous);
    }
    else
    {
        h_prev = h;
        int pos_p_prev = pos_p;
        fseek(hous, (long)(h.link * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fread(&h, sizeof(house), 1, hous);
        pos_p = h_prev.link;
        while (strcmp(h.keyP, key_1) != 0 && !feof(hous))
        {
            h_prev = h;
            pos_p_prev = pos_p;
            fseek(hous, (long)(h.link * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
            fread(&h, sizeof(house), 1, hous);
            pos_p = h_prev.link;
        }
        h_prev.link = h.link;
        h.flag = -1;
        fseek(hous, (long)(pos_p_prev * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&h_prev, sizeof(house), 1, hous);
        fseek(hous, (long)(pos_p * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
        fwrite(&h, sizeof(house), 1, hous);
        int s[DELETED_LEN];
        fseek(hous, 0, SEEK_SET);
        fread(&s, sizeof(int), DELETED_LEN, hous);
        int i = 0;
        while (s[i] > 0 && s[i] <= k_P) i++;
        s[i] = pos_p;
        fseek(hous, 0, SEEK_SET);
        fwrite(&s, sizeof(int), DELETED_LEN, hous);
    }
    printf("Success\n");
    fclose(fl);
    fclose(ind);
    fclose(hous);
}

void show_I()
{
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    realtorInd t;
    fread(&t, sizeof(realtorInd), 1, ind);
    while (!feof(ind))
    {
        fread(&t, sizeof(realtorInd), 1, ind);
        if (t.flag != -1)
            printf("Ключ: %s\n Адресс: %d\n", t.key, t.adress);
    }
    fclose(ind);
}

void show_R()
{
    FILE* fl;
    char name[] = "realtor.txt";
    fl = fopen(name, "rb");
    realtorFL r;
    fseek(fl, (long)(sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    int k = 1;
    while (!feof(fl) && k < k_T)
    {
        k++;
        fread(&r, sizeof(realtorFL), 1, fl);
        if (r.flag != -1)
            printf("Ключ: %s\n Имя: %s\n Количество проданных домов: %d\n", r.keyT, r.nameT, r.q_houses);
    }
    fclose(fl);
}

void show_H()
{
    printf("Ввести ключ риелтора:\n");
    FILE* ind;
    char name[] = "realtorInd.txt";
    ind = fopen(name, "rb");
    char key_[KEY_LEN];
    scanf("%10s", key_);
    realtorInd r;
    int ll;
    while (!feof(ind))
    {
        fread(&r, sizeof(realtorInd), 1, ind);
        if (strcmp(r.key, key_) == 0)
        {
            ll = r.adress;
            break;
        }
    }
    FILE* fl;
    char name1[] = "realtor.txt";
    fl = fopen(name1, "rb+");
    realtorFL r_fl;
    fseek(fl, (long)(ll * sizeof(realtorFL) + DELETED_LEN * sizeof(int)), SEEK_SET);
    fread(&r_fl, sizeof(realtorFL), 1, fl);
    int ln = r_fl.link;
    int k = 1;

    if (r_fl.flag != -1)
    {
        FILE* hous;
        char name2[] = "houses.txt";
        hous = fopen(name2, "rb+");
        house p;
        while (ln != -1)
        {
            fseek(hous, (long)(ln * sizeof(house) + DELETED_LEN * sizeof(int)), SEEK_SET);
            fread(&p, sizeof(house), 1, hous);
            ln = p.link;
            printf("Ключ: %s\n Адресс: %s\n Цена: %d\n", (p.keyP), (p.adressP), (p.cost), (p.link));
            k++;
        }
        fclose(hous);
    }
    else printf("Риелтора не существует\n");
    fclose(ind);
    fclose(fl);
}



int main()
{
    int option = 0;
    setlocale(LC_ALL, "Russian");




    while (1) {
        printf("\n\t1 - Добавить риелтора (insert_R) \n\t2 - Добавить дом (insert_H) \n\t3 - Просмотреть риелторов (show_H)");
        printf("\n\t4 - Просмотреть дома проданные риелтором (show_H)\n\t5 - Обновить данные риелтора (update_R)");
        printf("\n\t6 - Обновить данные дома (update_H)\n\t7 - Удалить риелтора (del_R)\n\t8 - Удалить дом (del_H)");
        printf("\n\t9 - Показать информацию про риелтора (get_R)\n\t10 - Показать информацию про дом (get_H)");
        printf("\n\t11 - Выйти из приложения\n\n");

        scanf("%d", &option);
        switch (option) {
            case 1: insert_R(); break;
            case 2: insert_H(); break;
            case 3: show_R(); break;
            case 4: show_H(); break;
            case 5: update_R(); break;
            case 6: update_H(); break;
            case 7: del_R(); break;
            case 8: del_H(); break;
            case 9:
            {
                realtorFL t;
                t = get_R();
                if (t.flag != -1) printf("Ключ: %s\n Имя: %s\n Количество проданных домов: %d\n", (t.keyT), (t.nameT), (t.q_houses));
                break;
            }
            case 10:
            {
                house h;
                h = get_H();
                if (h.cost != -1) printf("Ключ: %s\n Адресс: %s\n Цена: %d\n\n", (h.keyP), (h.adressP), (h.cost));
                else printf("Риелтор с данным ключем не продавал этот дом\n");
                break;
            }
            case 11:
            {
                return 0;
            }
        }
        while (fgetc(stdin) != '\n');
        if (option < 0 || option>11) {
            printf("Введено неверные данные\n");
        }
    }
}