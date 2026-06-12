# Java 2D RPG Game

Trò chơi nhập vai 2D top-down được xây dựng bằng Java Swing/AWT, không sử dụng thư viện game bên ngoài.

---

## Tính năng nổi bật

- **Hệ thống chiến đấu real-time** — tấn công cận chiến (kiếm, lancer) và tấn công tầm xa (magic rock tốn MP)
- **AI quái vật** — tìm đường A*, phát hiện người chơi trong tầm nhìn, bắn đạn
- **Hệ thống nhân vật** — cấp độ, kinh nghiệm, chỉ số HP/MP/STR/DEX/INT/VIT
- **Trang bị & Kho đồ** — 20 slot, đổi vũ khí giữa trận
- **NPC & Thương nhân** — đối thoại, mua bán vật phẩm
- **Nhiều bản đồ** — tối đa 10 bản đồ, dịch chuyển giữa các khu vực
- **Hệ thống sự kiện** — bẫy hố, hồ chữa máu, điểm dịch chuyển
- **Hiệu ứng ánh sáng** — độ tối môi trường, đèn lồng tạo vùng sáng
- **Âm thanh** — nhạc nền, hiệu ứng âm thanh, điều chỉnh âm lượng
- **3 độ khó** — Easy / Hard / Evil

---

## Cấu trúc dự án

```
RPG/
├── main/           # Engine chính (GamePanel, UI, Input, Sound, Config…)
├── entity/         # Entity base, Player, NPC, Projectile
├── monsters/       # Các loại quái (Mons1, Darkin, Werewolf, Zombie)
├── object/         # Vật phẩm & vũ khí (OBJ_*)
├── tile/           # TileManager, định nghĩa Tile
├── AI/             # A* Pathfinder, Node
├── enviroment/     # Environment, Lightning
├── maps/           # Dữ liệu bản đồ dạng văn bản (.txt)
├── char/           # Sprite nhân vật & NPC
├── tiles/          # Tile graphics (overworld, dungeon, house)
├── sounds/         # Nhạc nền & hiệu ứng âm thanh (.wav)
├── font/           # Font Inconsolata.ttf
└── config.txt      # Lưu cài đặt âm lượng
```

---

## Yêu cầu hệ thống

| Mục | Yêu cầu |
|---|---|
| Java | JDK 11 trở lên |
| RAM | 256 MB trở lên |
| Màn hình | 960 × 720 px trở lên |
| OS | Windows / macOS / Linux |

---

## Cách chạy

### Dùng IDE (IntelliJ IDEA / Eclipse / VS Code)

1. Mở thư mục `RPG/` như một Java project.
2. Đặt thư mục gốc (`RPG/`) là source root để classpath load đúng tài nguyên.
3. Chạy class `main.Main`.

### Dùng dòng lệnh

```bash
# Đứng tại thư mục RPG/
javac -d out $(find . -name "*.java")
java -cp out main.Main
```

> **Windows PowerShell:**
> ```powershell
> Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName } | Out-File sources.txt
> javac -d out @sources.txt
> java -cp "out;." main.Main
> ```

---

## Điều khiển

| Phím | Hành động |
|---|---|
| `W` / `A` / `S` / `D` | Di chuyển |
| `V` / `Enter` | Tấn công / Tương tác NPC |
| `F` | Bắn đạn phép (tốn 1 MP) |
| `X` | Mở/đóng kho đồ |
| `P` | Tạm dừng |
| `Esc` | Tùy chọn |

### Trong menu Tùy chọn

| Phím | Hành động |
|---|---|
| `A` / `D` | Giảm / Tăng âm lượng |

---

## Gameplay

### Tiến trình

1. Chọn độ khó ở màn hình tiêu đề.
2. Khám phá bản đồ, tiêu diệt quái vật để nhận kinh nghiệm.
3. Lên cấp → chỉ số HP, STR, DEX tăng.
4. Mua vật phẩm từ thương nhân, mở rương kho báu.
5. Tìm chìa khóa để mở cửa đến khu vực mới.

### Vật phẩm

| Vật phẩm | Công dụng |
|---|---|
| Heart / Health Potion | Hồi phục HP |
| Mana Potion | Hồi phục MP |
| Sword | Vũ khí cận chiến cơ bản |
| Lancer | Vũ khí cận chiến tầm dài hơn |
| Lantern | Tạo vùng sáng xung quanh |
| Key | Mở cửa khóa |
| Tent | Hồi phục khi nghỉ ngơi |
| Coin | Tiền tệ để mua đồ |

### Quái vật

| Quái | HP | ATK | EXP |
|---|---|---|---|
| Mons1 | 5 | 4 | 5 |
| Darkin | 25 | 5 | 15 |
| Werewolf | — | — | — |
| Zombie | — | — | — |

---

## Kiến trúc kỹ thuật

- **Game loop** — 60 FPS dùng `Thread` + `System.nanoTime()`
- **Rendering** — `JPanel` + `BufferedImage`, Z-sort entity theo `worldY`
- **Collision** — `java.awt.Rectangle` intersection trên tile grid và hitbox
- **Pathfinding** — A* với open/closed list, reset mỗi frame cần tìm đường
- **Âm thanh** — `javax.sound.sampled` với `FloatControl` để điều chỉnh dB
- **Bản đồ** — file `.txt` lưu số tile ID, 50×50 tiles mỗi bản đồ
- **Tài nguyên** — tất cả load qua `getClass().getResourceAsStream()` (classpath)

---

## Cấu hình âm lượng

File `config.txt` lưu hai dòng:

```
7       ← Music volume (0–10)
5       ← Sound effects volume (0–10)
```

Cài đặt tự động lưu khi thoát màn hình tùy chọn.

---

## Phát triển thêm

Một số hướng mở rộng có thể thực hiện:

- Thêm bản đồ mới (tạo file `.txt` và đăng ký trong `TileManager`)
- Thêm quái mới (kế thừa `Entity`, đặt vào `AssetSetter`)
- Thêm vật phẩm mới (kế thừa `Entity` với `type_*` phù hợp)
- Thêm sự kiện (đăng ký tọa độ trong `EventHandle`)
- Hệ thống lưu game (serialize trạng thái `Player` và bản đồ)

---

## Tác giả

Dự án học tập Java 2D game development — xây dựng hoàn toàn từ đầu bằng Java Swing/AWT.
